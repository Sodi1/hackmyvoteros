import os

import torch.nn.functional as F
from sentence_transformers import SentenceTransformer

model_path = os.path.abspath("myvote_analytics/models/sbert_large_mt_ru_retriever")
model = SentenceTransformer(model_path, device="cpu")
def similar_sentences(source_sentence, sentences):  # pragma: no cover

    embeddings = model.encode(sentences, convert_to_tensor=True)
    source_embedding = model.encode(source_sentence, convert_to_tensor=True)
    result = []
    for i in range(len(embeddings)):
        cosine_sim = F.cosine_similarity(source_embedding, embeddings[i], dim=0)
        result.append([float(cosine_sim.item()), sentences[i]])
    return source_sentence, result

def search_similar_questions(query, questions, significance):  # pragma: no cover

    embeddings = model.encode([question.name for question in questions], convert_to_tensor=True)
    source_embedding = model.encode(query, convert_to_tensor=True)
    result = []
    for i in range(len(embeddings)):
        cosine_sim = F.cosine_similarity(source_embedding, embeddings[i], dim=0)
        if float(cosine_sim.item()) > significance:
            result.append([float(cosine_sim.item()), questions[i]])
    return result
