package net.tobiaskohl.bingocreator.backend.text;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TextBreakerTest {

	@Test
	void test() {
		assertArrayEquals(new int[] {4}, TextBreaker.breakTextAtSpaces("0123"));
		assertArrayEquals(new int[] {1,3,5,7}, TextBreaker.breakTextAtSpaces("0 1 2 3"));
		assertArrayEquals(new int[] {5,12}, TextBreaker.breakTextAtSpaces("Hello World!"));
	}

}
