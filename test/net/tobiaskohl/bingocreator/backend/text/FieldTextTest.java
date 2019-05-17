package net.tobiaskohl.bingocreator.backend.text;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldTextTest {

	@Test
	void test() {
		FieldTextFactory factory = new FieldTextFactory();
		factory.setBoundaries(100, 20);
		assertArrayEquals(new String[] {"Hello World!"}, factory.createText("Hello World!").getLines());
		factory.setBoundaries(20, 20);
		assertArrayEquals(new String[] {"Hello", "World!"}, factory.createText("Hello World!").getLines());
	}

}
