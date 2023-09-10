from fastapi import APIRouter


from myvote_analytics.services.spellcheck.dependencies import (
    check_spell
)
from myvote_analytics.web.api.spellcheck.schema import (
    SpellcheckRequestDTO,
    SpellcheckResponseDTO
)

router = APIRouter()


@router.post("/", response_model=SpellcheckResponseDTO)
async def make_spellcheck(
    incoming_message: SpellcheckRequestDTO,
) -> SpellcheckResponseDTO:
    spells = check_spell(
        incoming_message.texts
    )
    return SpellcheckResponseDTO(
        spells=spells,
    )
