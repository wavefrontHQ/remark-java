/*
 * Copyright 2011 OverZealous Creations, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.overzealous.remark.nodewalker;

import com.overzealous.remark.Options;
import com.overzealous.remark.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Phil DeJarnett
 */
public class TextCleanerTest {

	static final TextCleaner BASIC = new TextCleaner(Options.markdown());

	static final TextCleaner FULL = new TextCleaner(Options.pegdownAllExtensions());

	private String loadOut(String name) {
		return load(name, "out");
	}

	private String loadBasicIn() {
		return load("basic", "in");
	}

	private String loadFullIn() {
		return load("full", "in");
	}

	private String load(String name, String type) {
		return TestUtils.readResourceToString("/textcleaner/"+name+"."+type+".txt");
	}

	private void assertEqualsAndPrint(String expected, String result) {
		System.out.println(expected);
		System.out.println(result);
		System.out.println();
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testCleanBasic() throws Exception {
		assertEqualsAndPrint(loadOut("cleanBasic"), BASIC.clean(loadBasicIn()));
	}

	@Test
	public void testCleanFull() throws Exception {
		assertEqualsAndPrint(loadOut("cleanFull"), FULL.clean(loadFullIn()));
	}

	@Test
	public void testCleanCodeBasic() throws Exception {
		assertEqualsAndPrint(loadOut("cleanCodeBasic"), BASIC.cleanCode(loadBasicIn()));
	}

	@Test
	public void testCleanCodeFull() throws Exception {
		assertEqualsAndPrint(loadOut("cleanCodeFull"), FULL.cleanCode(loadFullIn()));
	}

	@Test
	public void testCleanInlineCodeSimple() throws Exception {
		Assert.assertEquals("`hello &  > world`", BASIC.cleanInlineCode("hello &amp; \n&gt; world"));
	}

	@Test
	public void testCleanInlineCodeLeadingTick() throws Exception {
		Assert.assertEquals("`` `tick``", BASIC.cleanInlineCode("`tick"));
	}

	@Test
	public void testCleanInlineCodeTrailingTick() throws Exception {
		Assert.assertEquals("``tick` ``", BASIC.cleanInlineCode("tick`"));
	}

	@Test
	public void testCleanInlineCodeInlineTick() throws Exception {
		Assert.assertEquals("``ti`ck``", BASIC.cleanInlineCode("ti`ck"));
	}

	@Test
	public void testCleanInlineCodeLotsOfTicksTick() throws Exception {
		Assert.assertEquals("```` ``t```i`ck` ````", BASIC.cleanInlineCode("``t```i`ck`"));
	}
}
