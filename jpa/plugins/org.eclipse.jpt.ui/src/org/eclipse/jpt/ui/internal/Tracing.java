/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.core.runtime.Platform;

/**
 * This tracing class manages to convert the string value into boolean values or
 * integer values that are associated with the tracing debug flags. Those flags
 * are specified in the .options file. The supported keys are defined here as
 * constants for quick reference.
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class Tracing
{
	/**
	 * A constant used to retrieve the value associated with "/debug".
	 */
	public static final String DEBUG = "/debug";

	/**
	 * A constant used to retrieve the value associated with "/debug/layout".
	 */
	public static final String DEBUG_LAYOUT = "/debug/layout";

	/**
	 * A constant used to retrieve the value associated with "/unit-tests".
	 */
	public static final String UNIT_TESTS = "/unit-tests";

	/**
	 * Can't instantiate this <code>Tracing</code> class.
	 */
	private Tracing()
	{
		super();
		throw new UnsupportedOperationException("Tracing cannot be instantiated");
	}

	/**
	 * Retrieves the debug value associated with the given flag. The default
	 * value is <code>false</code>.
	 *
	 * @param flag The flag to retrieve the debug value, which should be
	 * contained in the .options file, the flag should start with "/"
	 * @return <code>true</code> if the given flag is active; <code>false</code>
	 * otherwise
	 */
	public static boolean booleanDebugOption(String flag)
	{
		return booleanDebugOption(flag, false);
	}

	/**
	 * Retrieves the debug value associated with the given flag.
	 *
	 * @param flag The flag to retrieve the debug value, which should be
	 * contained in the .options file, the flag should start with "/"
	 * @param defaultValue The default value if the value associated with the
	 * given flag could not be found
	 * @return <code>true</code> if the given flag is active; <code>false</code>
	 * otherwise
	 */
	public static boolean booleanDebugOption(String flag, boolean defaultValue)
	{
		String result = Platform.getDebugOption(JptUiPlugin.PLUGIN_ID + flag);

		if (result == null)
		{
			return defaultValue;
		}

		return Boolean.valueOf(result.trim());
	}

	/**
	 * Retrieves the debug value associated with the given flag. The default value
	 * is 0.
	 *
	 * @param flag The flag to retrieve the debug value, which should be
	 * contained in the .options file, the flag should start with "/"
	 * @return The value associated with the given flag, or the given default
	 * value
	 */
	public static int intDebugOption(String flag)
	{
		return intDebugOption(flag, 0);
	}

	/**
	 * Retrieves the debug value associated with the given flag.
	 *
	 * @param flag The flag to retrieve the debug value, which should be
	 * contained in the .options file, the flag should start with "/"
	 * @param defaultValue The default value if the value associated with the
	 * given flag could not be found
	 * @return The value associated with the given flag, or the given default
	 * value
	 */
	public static int intDebugOption(String flag, int defaultValue)
	{
		String result = Platform.getDebugOption(JptUiPlugin.PLUGIN_ID + flag);

		if (result == null)
		{
			return defaultValue;
		}

		return Integer.valueOf(result);
	}

	/**
	 * Logs the given messages, appends it with this plug-in id.
	 *
	 * @param message The message to be logged
	 */
	public static void log(String message)
	{
		System.out.print("[" + JptUiPlugin.PLUGIN_ID + "] ");
		System.out.println(message);
	}

	/**
	 * Retrieves the debug value associated with the given flag. The default value
	 * is an empty string.
	 *
	 * @param flag The flag to retrieve the debug value, which should be
	 * contained in the .options file, the flag should start with "/"
	 * @return The value associated with the given flag, or the given default
	 * value
	 */
	public static String stringDebugOption(String flag)
	{
		return stringDebugOption(flag, "");
	}

	/**
	 * Retrieves the debug value associated with the given flag.
	 *
	 * @param flag The flag to retrieve the debug value, which should be
	 * contained in the .options file, the flag should start with "/"
	 * @param defaultValue The default value if the value associated with the
	 * given flag could not be found
	 * @return The value associated with the given flag, or the given default
	 * value
	 */
	public static String stringDebugOption(String flag, String defaultValue)
	{
		String result = Platform.getDebugOption(JptUiPlugin.PLUGIN_ID + flag);

		if (result == null)
		{
			result = defaultValue;
		}

		return result;
	}
}
