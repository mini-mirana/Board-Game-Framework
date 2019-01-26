
#include <cstdlib>
#include <iostream>
#include <ctime>
#include <thread>
#include "jnipp.h"
#include "board.h"
#include "move.h"
#include "gui.h"
#include "ai.h"
#include "master.h"

using namespace Chess;


unsigned long perft(Board &b, Boardhistory &h, int depth, Color turn) {
	turn = (turn == Color::white) ? Color::black : Color::white;
	if (!depth)
		return 1;
	int leafs = 0;
	for (Move m : valid_moves(b, turn)) {
		if (b.get(m.to).piece == Piece::king || b.get(m.to).piece == Piece::king_castle) {
			++leafs;
			continue;
		}
		do_move(m, b, h);
		leafs += perft(b, h, depth - 1, turn);
		undo_move(b, h);
	}
	return leafs;
}


int chessEXE() {
	std::cout << "\nChesscpu 1.0\n\n";
	std::cout << "Commands:\nyxyx: fromto move.\n0: regret move (last AI move will be reverted as well).\n1: change color (AI will make this move)\n2: exit.\n\n";
	Board b;
	Boardhistory h;
	init_classic_board(b);

	while (Master::ManageBoard.get<bool>(Master::gameStarted) == false);
	int num_player;
	while (true) {
		std::cout << "choose number of player: ";
		std::cin >> num_player;
		if (num_player == 1 || num_player == 2)
			break;
		else
			std::cout << "wrong choice" << std::endl;
	}

	Color human1_color;
	Color ai_color;
	if (num_player == 2)
		human1_color = Color::black;
	else
		ai_color = Color::black;
	Color turn = Color::white;

	bool human1_has_king;
	bool ai_has_king;
	if (num_player == 2)
		human1_has_king = true;
	else
		ai_has_king = true;
	bool human_has_king = true;

	if (false) {
		unsigned long t = time(0);
		std::cout << "DEBUG: Perft(5) result (expecting 4897256): " << perft(b, h, 5, Color::black);
		t = time(0) - t;
		std::cout << "\nTime " << t << "\n";
		return 0;
	}

	if (false) {
		Move mv;
		unsigned long t = time(0);
		ai_move(b, h, turn, 7, mv);
		t = time(0) - t;
		std::cout << "\nAI Time: " << t << "\n";
		return 0;
	}

	Move mv{ 0,0 };
	if (num_player == 2)
		while (human1_has_king && human_has_king) {
			print_board(b, mv);
			if (turn == human1_color)
				mv = read_move(valid_moves(b, human1_color), human1_color);
			else
				mv = read_move(valid_moves(b, turn), turn);

			if (mv.from == 0) {
				undo_move(b, h);
				undo_move(b, h);
				continue;
			}
			else if (mv.from == 1) {
				human1_color = human1_color == Color::white ? Color::black : Color::white;
				continue;
			}
			else if (mv.from == 2) {
				human_has_king = false; /* check here for loser */
				break;
			}

			do_move(mv, b, h);
			turn = turn == Color::white ? Color::black : Color::white; /* check here althogh */

			human1_has_king = human1_has_king = false;
			for (size_t i = 21; i < 99; ++i) { // board.h about these magic numbers
				if (b.get(i).piece == Piece::king || b.get(i).piece == Piece::king_castle) {
					if (b.get(i).piece_color == human1_color) {
						human1_has_king = true;
					}
					else {
						human_has_king = true;
					}
				}
			}

		}
	else
		while (ai_has_king && human_has_king) {
			print_board(b, mv);
			if (turn == ai_color) {
				ai_move(b, h, turn, 7, mv);
				Master::mB.call<void>(Master::movePiece, (int)(mv.from / 10), (int)(mv.from % 10), (int)(mv.to / 10),(int)(mv.to % 10));
			}
			else
				mv = read_move(valid_moves(b, turn), turn);

			if (mv.from == 0) {
				undo_move(b, h);
				undo_move(b, h);
				continue;
			}
			else if (mv.from == 1) {
				ai_color = ai_color == Color::white ? Color::black : Color::white;
				continue;
			}
			else if (mv.from == 2) {
				human_has_king = false; /* check here for loser */
				break;
			}

			do_move(mv, b, h);
			turn = turn == Color::white ? Color::black : Color::white; /* check here althogh */

			ai_has_king = human_has_king = false;
			for (size_t i = 21; i < 99; ++i) { // board.h about these magic numbers
				if (b.get(i).piece == Piece::king || b.get(i).piece == Piece::king_castle) {
					if (b.get(i).piece_color == ai_color) {
						ai_has_king = true;
					}
					else {
						human_has_king = true;
					}
				}
			}

		}
	print_board(b, mv);

	std::cout << "\n\n";
	if (num_player == 2) {
		if (!human_has_king)
			std::cout << "You lose.";
		if (!human1_has_king)
			std::cout << "You win.";
		std::cout << "\n\nBye!\n\n";
	}
	else {
		if (!human_has_king)
			std::cout << "You lose.";
		if (!ai_has_king)
			std::cout << "You win.";
		std::cout << "\n\nBye!\n\n";
	}
}


int main() {
	// An instance of the Java VM needs to be created.
	//jni::Vm vm("C:\\Program Files\\Java\\jdk-9.0.4\\bin\\server\\jvm.dll");
	//jni::Vm vm("C:\\Program Files (x86)\\Java\\jre7\\bin\\client\\jvm.dll");

	// Create an instance of java.lang.Integer

	// Call the `toString` method on that integer
	//std::string str = i.call<std::string>("toString");
	std::string str = Master::i.call<std::string>("toString");
	std::cout << str << std::endl;
	system("pause");

	std::thread chessThread(chessEXE);
	chessThread.detach();
	int e = Master::Ball.call<int>(Master::exe, 2, 1, 0, 0, 8, 8, 16, "",
		"\u2654\u2655\u2656\u2657\u2662\u2663\u2664\u2665\n♟♟♟♟♟♟♟♟",
		"\u2654\u2655\u2656\u2657\u2662\u2663\u2664\u2665\n♟♟♟♟♟♟♟♟",
		"",
		"");
	//std::thread t1([&]() {Ball.call<int>(exe, 2, 1, 0, 0, 8, 8, 16, ""); });
	//Master::ManageBoard.getStaticField
	//jni::field_t
	//Master::ManageBoard.get<bool>(Master::answerQueue);
	
	// The Java VM is automatically destroyed when it goes out of scope.
	return 0;
}