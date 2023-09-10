from typing import List, Tuple

from pydantic import BaseModel


class SimilaritySentenceRequestDTO(BaseModel):
    """ДТО для свагера что бы развернуть это в правильный payload"""

    source_sentence: str
    sentences: List[str]  # noqa: WPS110


class SimilaritySentenceResponseDTO(BaseModel):
    """ДТО для форматирования ответа на запрос поиска похожих выражений"""

    source_sentence: str
    sentences: List[Tuple[float, str]]  # noqa: WPS110
