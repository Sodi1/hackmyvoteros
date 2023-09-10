from typing import List
import re
from spellchecker import SpellChecker


spell = SpellChecker(language=['ru', 'en'])
pattern = re.compile(r"[\w']+|[.,!?;]")


def join_punctuation(seq, characters='.,;?!'):
    characters = set(characters)
    seq = iter(seq)
    current = next(seq)
    for nxt in seq:
        if nxt in characters:
            current += nxt
        else:
            yield current
            current = nxt

    yield current


def check_spell(spells: List[str]):  # pragma: no cover
    for i_spell in range(len(spells)):
        tokens = pattern.findall(spells[i_spell])
        unk_token_set = spell.unknown(tokens)
        misspelled = [word for word in unk_token_set if len(word) > 2]
        corrected = [spell.correction(miss) for miss in misspelled]
        if None in corrected:
            corrected = [c for c in corrected if c is not None]
        tokens_tmp = [token.lower() for token in tokens]
        for x in range(len(corrected)):
            missed_idx = tokens_tmp.index(misspelled[x])
            tokens[missed_idx] = tokens[missed_idx] \
                    .replace(misspelled[x], corrected[x])
            spells[i_spell] = ' '.join(join_punctuation(tokens))
            # spells[i_spell] = ' '.join(tokens)
            spells[i_spell] = spells[i_spell][:1].upper() + spells[i_spell][1:]

    return spells
