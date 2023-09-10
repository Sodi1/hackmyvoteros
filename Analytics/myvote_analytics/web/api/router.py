from fastapi.routing import APIRouter

from myvote_analytics.web.api import (
    docs,
    monitoring,
    question,
    similarity_sentences,
    emotions,
    clustering,
    spellcheck
)

api_router = APIRouter()
api_router.include_router(monitoring.router)
api_router.include_router(docs.router)
api_router.include_router(question.router, prefix="/question", tags=["question"])
api_router.include_router(
    similarity_sentences.router,
    prefix="/similarity-sentences",
    tags=["similarity-sentences"],
)
api_router.include_router(
    emotions.router,
    prefix="/emotions",
    tags=["emotions"],
)
api_router.include_router(
    clustering.router,
    prefix="/clustering",
    tags=["clusters"],
)
api_router.include_router(
    spellcheck.router,
    prefix="/spellcheck",
    tags=["spellcheck"],
)
