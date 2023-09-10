from fastapi import APIRouter

from myvote_analytics.services.sbert_large_mt_ru_retriever.dependencies import (
    similar_sentences,
)
from myvote_analytics.web.api.similarity_sentences.schema import (
    SimilaritySentenceRequestDTO,
    SimilaritySentenceResponseDTO,
)

router = APIRouter()


@router.post("/", response_model=SimilaritySentenceResponseDTO)
async def get_similarity_sentences(
    incoming_message: SimilaritySentenceRequestDTO,
) -> SimilaritySentenceResponseDTO:
    source_sentence, sentences = similar_sentences(
        incoming_message.source_sentence, incoming_message.sentences
    )
    print(source_sentence)
    print(sentences)
    return SimilaritySentenceResponseDTO(
        source_sentence=source_sentence,
        sentences=sentences,
    )
