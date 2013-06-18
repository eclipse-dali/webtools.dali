/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests;

import java.lang.reflect.Field;
import junit.framework.TestCase;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This test case will verify that a validation message class is initialized
 * properly. It checks that each validation message has a template and
 * description. The templates are loaded from one bundle and the descriptions
 * are loaded from another (as specified when
 * {@link org.eclipse.jpt.common.core.internal.utility.ValidationMessageLoader#load(Class, String, String, String, org.eclipse.jpt.common.core.internal.utility.ValidationMessageLoader.PreferencesAdapter) ValidationMessageLoader#load(...)}
 * is called). If either bundle is missing the string corresponding
 * to the validation message, the validation message template (or description)
 * is initialized with an error message that begins with <code>"NLS"</code>.
 * This test will simply verify that none of the strings begin with
 * <code>"NLS"</code>.
 * <p>
 * Construct the test case with the class that loads the validation message
 * bundle.
 * 
 * @see org.eclipse.jpt.common.core.internal.utility.ValidationMessageLoader
 */
@SuppressWarnings("nls")
public class ValidationMessageClassTest
	extends TestCase
{
	private final Class<?> clazz;

	public ValidationMessageClassTest(Class<?> clazz) {
		super("Validation message class test: " + clazz.getSimpleName());
		this.clazz = clazz;
	}

	@Override
	protected void runTest() throws Throwable {
		Field[] fields = this.clazz.getFields();
		for (Field field : fields) {
			ValidationMessage msg = (ValidationMessage) field.get(null);

			String template = (String) ObjectTools.get(msg, "template");
			assertNotNull(template);
			this.checkString("template", field, template);

			String description = msg.getDescription();
			assertNotNull(description);
			this.checkString("description", field, description);
		}
	}

	private void checkString(String msg, Field field, String s) {
		assertFalse("Missing validation message " + msg + ": " + field.getName() + " (see error log)", s.startsWith("NLS"));
	}
}
