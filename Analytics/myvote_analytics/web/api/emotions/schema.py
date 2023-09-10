from typing import List, Any

from pydantic import BaseModel

class EmotionsRequestDTO(BaseModel):
    """Формат запроса для определения эмоциональной окраски текста"""
    texts: List[str]


class EmotionsResponseDTO(BaseModel):
    """Формат ответта для определителя эмоций"""
    emotions: List[List[Any]]  # noqa: WPS110
