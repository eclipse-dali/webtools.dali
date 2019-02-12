/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import org.eclipse.jpt.common.utility.internal.model.value.swing.TableModelAdapter;
import org.eclipse.jpt.common.utility.tests.internal.model.value.swing.TableModelAdapterTests.PersonColumnAdapter;

/**
 * Make it easy to test the table model adapter and
 * renderers without any editing allowed.
 */
public class ReadOnlyTableModelAdapterUITest
	extends TableModelAdapterUITest
{
	public static void main(String[] args) throws Exception {
		new ReadOnlyTableModelAdapterUITest().exec(args);
	}

	protected ReadOnlyTableModelAdapterUITest() {
		super();
	}

	@Override
	protected TableModelAdapter.ColumnAdapter buildColumnAdapter() {
		return new PersonColumnAdapter() {
			@Override
			public boolean columnIsEditable(int index) {
				return false;
			}
		};
	}
}
