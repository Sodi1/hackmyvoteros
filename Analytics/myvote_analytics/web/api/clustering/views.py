from fastapi import APIRouter

from myvote_analytics.services.clustering.dependencies import (
    agglomerative_clustering,
    fast_clustering
)
from myvote_analytics.web.api.clustering.schema import (
    ClusteringRequestDTO,
    ClusteringResponseDTO,
    FastClusteringRequestDTO
)

router = APIRouter()


@router.post("/", response_model=ClusteringResponseDTO)
async def get_agg_clusters(
    incoming_message: ClusteringRequestDTO,
) -> ClusteringResponseDTO:
    clusters = agglomerative_clustering(incoming_message.texts,
                                        incoming_message.n_clusters,
                                        incoming_message.distance_threshold)
    return ClusteringResponseDTO(
        clusters=clusters
    )


@router.post("/fast", response_model=ClusteringResponseDTO)
async def get_clusters_fast(
    incoming_message: FastClusteringRequestDTO
) -> ClusteringResponseDTO:
    """
    *Warning:* минимум 25 элементов, максимум - 176.
    """
    clusters = fast_clustering(incoming_message.texts, incoming_message.min_size)
    return ClusteringResponseDTO(
        clusters=clusters
    )
