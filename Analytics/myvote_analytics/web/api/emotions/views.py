from fastapi import APIRouter

from myvote_analytics.services.emotions.dependencies import (
    detect_emotions
)
from myvote_analytics.web.api.emotions.schema import (
    EmotionsRequestDTO,
    EmotionsResponseDTO,
)

router = APIRouter()


@router.post("/", response_model=EmotionsResponseDTO)
async def get_emotions(
    incoming_message: EmotionsRequestDTO,
) -> EmotionsResponseDTO:
    """"
        Определяет эмоциональную окраску предложения
    """
    emotions = detect_emotions(incoming_message.texts)
    return EmotionsResponseDTO(
        emotions=emotions
    )
