#pragma once

#include "move.h"

namespace Chess {
	int ai_move(Board& b, Boardhistory& bh, Color turn, int depth, Move& _bm, int alpha = -400, int beta = 400);
}