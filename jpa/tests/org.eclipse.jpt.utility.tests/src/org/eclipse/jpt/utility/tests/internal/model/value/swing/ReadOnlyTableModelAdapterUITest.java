/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value.swing;

import org.eclipse.jpt.utility.internal.model.value.swing.ColumnAdapter;
import org.eclipse.jpt.utility.tests.internal.model.value.swing.TableModelAdapterTests.PersonColumnAdapter;

/**
 * Make it easy to test the table model adapter and
 * renderers without any editing allowed.
 */
public class ReadOnlyTableModelAdapterUITest extends TableModelAdapterUITest {

	public static void main(String[] args) throws Exception {
		new ReadOnlyTableModelAdapterUITest().exec(args);
	}

	protected ReadOnlyTableModelAdapterUITest() {
		super();
	}

	@Override
	protected ColumnAdapter buildColumnAdapter() {
		return new PersonColumnAdapter() {
			@Override
			public boolean isColumnEditable(int index) {
				return false;
			}
		};
	}

}
