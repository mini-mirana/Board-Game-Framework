// File: gui.cpp
// This is quick, ugly, pragmatic, temporary.

#include <iostream>
#include <sstream>
#include <cctype>
#include <map>
#include "gui.h"
#include "master.h"

namespace Chess {

	using namespace std;

	static const std::map<Piece, char> sprites = {
		{ Piece::pawn,      'A' },
	{ Piece::pawn_en_passant,   'P' },
	{ Piece::rook,      'H' },
	{ Piece::rook_castle,   'H' },
	{ Piece::knight,        'F' },
	{ Piece::bishop,        'I' },
	{ Piece::king,      'K' },
	{ Piece::king_castle,   'K' },
	{ Piece::none,      '+' },
	{ Piece::out_of_board,      '#' },
	{ Piece::queen,         'Q' }
	};

	void print_board(const Board& b, Move last_move) {
		cout << "\n   1 2 3 4 5 6 7 8";
		for (size_t i = 20; i < (size_t)board_size; ++i) {
			if (i % 10 == 9)
				continue;
			if (i / 10 == 10)
				break;
			if (i % 10 == 0) {
				cout << "\n " << i / 10 << " ";
				continue;
			}
			char s = sprites.at(b.get(i).piece);
			if (b.get(i).piece_color == Color::black)
				s = tolower(s);
			cout << s;
			if (last_move.from == i || last_move.to == i)
				cout << '<';
			else
				cout << ' ';
		}
		cout << "\n" << endl;
	}

	Move read_move(const MoveSet& valid_moves, Color turn) {
		if (cin.fail()) {
			cin.clear();
			string dummy;
			getline(cin, dummy);
		}

		int in;
		Move ret;
		cout << "Your move, " << (turn == Color::white ? "white" : "black") << ": " << std::endl;
		std::string s1 = "Your move, ";
		std::string s2 = turn == Color::white ? "white: " : "black: ";
		Master::Ball.call<void>(Master::showMsg,std::string(s1+s2));
		//cin >> in;
		while (Master::ManageBoard.get<std::string>(Master::moveQueue).size() == 0);
		std::string inFromJava = Master::ManageBoard.get<std::string>(Master::moveQueue);
		in = atoi(inFromJava.c_str());

		// Command to undo 1 or 2 moves (2 to revert AI+own)
		if (in == 0 || in == 1 || in == 2) {
			ret.from = in;
			return ret;
		}

		ret.to = in % 10 + in / 10 % 10 * 10;
		in /= 100;
		ret.from = in % 10 + in / 10 % 10 * 10;

		for (const auto m : valid_moves)
			if (m.from == ret.from && m.to == ret.to) {
				Master::ManageBoard.set<bool>(Master::queueTriggered, false);
				Master::ManageBoard.set<std::string>(Master::moveQueue, "");
				Master::ManageBoard.set<bool>(Master::answerQueue, true);
				return ret;
			}
		
		cout << "Invalid move\n";
		Master::Ball.call<void>(Master::showMsg, "Invalid move\n");
		Master::ManageBoard.set<bool>(Master::queueTriggered, false);
		Master::ManageBoard.set<std::string>(Master::moveQueue, "");
		Master::ManageBoard.set<bool>(Master::answerQueue, false);
		return read_move(valid_moves, turn);
	}

}