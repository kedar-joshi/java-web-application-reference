package dev.workingtheory.reference.app.support;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;

/**
 * Provides common test helpers.
 */
public interface TestHelpers
{
	public static Matcher<String> IS_NOT_BLANK_MATCHER = IsNot.not(Matchers.blankOrNullString());
}
