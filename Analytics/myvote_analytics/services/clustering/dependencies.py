import os

from sentence_transformers import SentenceTransformer, util
from sklearn.cluster import AgglomerativeClustering
from keybert import KeyBERT
import numpy as np


embedder_path = os.path.abspath("myvote_analytics/models/sbert_large_mt_ru_retriever")
# embedder_path_fast = os.path.abspath("myvote_analytics/models/all-MiniLM-L6-v2")
embedder  = SentenceTransformer(embedder_path, device="cpu")
kw_model = KeyBERT(model=embedder)
# embedder_fast  = SentenceTransformer(embedder_path_fast, device="cpu")

def agglomerative_clustering(sentences,
                             n_clusters,
                             distance_threshold):  # pragma: no cover
    sentences_embeddings = embedder.encode(sentences, convert_to_tensor=True)

    # Нормализовать эмбеддинги к единичной длине
    sentences_embeddings = \
        sentences_embeddings /  np.linalg.norm(sentences_embeddings,
                                               axis=1,
                                               keepdims=True)

    clustering_model = AgglomerativeClustering(n_clusters=n_clusters,
                                            distance_threshold=distance_threshold,
                                            linkage='complete')
                                            #    compute_distances=True)
    try:
        clustering_model.fit(sentences_embeddings)
    except ValueError:
        return {'0':sentences}

    cluster_assignment = clustering_model.labels_

    clustered_sentences = {}
    for sentence_id, cluster_id in enumerate(cluster_assignment):
        if cluster_id not in clustered_sentences:
            clustered_sentences[cluster_id] = []

        clustered_sentences[cluster_id].append(sentences[sentence_id])

    res_clustered_sentences = {}
    for i in clustered_sentences.items():
        keywds = kw_model.extract_keywords(', '.join(i[1]),
                                           keyphrase_ngram_range=(1, 1),
                                           stop_words=None)
        if len(keywds) == 0:
            res_clustered_sentences = clustered_sentences
            break
        res_clustered_sentences[keywds[0][0]] = i[1]

    return res_clustered_sentences


def fast_clustering(sentences, min_size):
    # print(sentences)
    # print("Max Sequence Length:", embedder.max_seq_length)
    embeddings = embedder.encode(sentences,
                                batch_size=8,
                                convert_to_tensor=True)

    embeddings = embeddings /  np.linalg.norm(embeddings,
                                              axis=1,
                                              keepdims=True)

    clusters = util.community_detection(embeddings,
                                        min_community_size=min_size,
                                        threshold=0.75)

    res_clusters = {}
    for i, cluster in enumerate(clusters):
        res_clusters[i] = [sentences[sentence_id] for sentence_id in cluster]

    return res_clusters
