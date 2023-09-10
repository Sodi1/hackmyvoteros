from pydantic import BaseModel, ConfigDict
from typing import List, Tuple, Any

class QuestionModelDTO(BaseModel):
    """
    DTO for dummy models.

    It returned when accessing dummy models from the API.
    """

    id: int
    name: str
    model_config = ConfigDict(from_attributes=True)


class QuestionSearchInputDTO(BaseModel):
    """DTO for creating new dummy model."""

    query: str
class QuestionSearchOutputDTO(BaseModel):
    """DTO for creating new dummy model."""
    score: float
    name: str
    id: int
