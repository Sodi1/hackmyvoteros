from typing import List, Dict, Optional, Union

from pydantic import BaseModel, Field

class ClusteringRequestDTO(BaseModel):
    texts: List[str]
    n_clusters: Optional[Union[int, None]] = Field(
        title='N Clusters',
        description='Количество N кластеров',
        default=None,
        ge=1,
        le=200)
    distance_threshold: Optional[Union[float, None]] = Field(
        title='Distance threshold',
        default=0.9,
        ge=0,
        le=1000)


class FastClusteringRequestDTO(BaseModel):
    min_size: int
    texts: List[str] = Field(
                title='Sentences',
                description='Список ответов')


class ClusteringResponseDTO(BaseModel):
    """DTO for cluster values."""
    clusters: Dict[str, List[str]]  # noqa: WPS110
