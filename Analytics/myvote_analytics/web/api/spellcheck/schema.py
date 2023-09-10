from typing import List, Dict

from pydantic import BaseModel

class SpellcheckRequestDTO(BaseModel):
    texts: List[str]


class SpellcheckResponseDTO(BaseModel):
    """DTO for spellcheck texts."""
    spells: List[str]  # noqa: WPS110
