/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value.swing;

import javax.swing.ListCellRenderer;
import org.eclipse.jpt.utility.internal.model.value.ExtendedListValueModelWrapper;
import org.eclipse.jpt.utility.internal.swing.SimpleListCellRenderer;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * 
 */
public class ComboBoxModelAdapterUITest2 extends ComboBoxModelAdapterUITest {

	public static void main(String[] args) throws Exception {
		new ComboBoxModelAdapterUITest2().exec(args);
	}

	public ComboBoxModelAdapterUITest2() {
		super();
	}

	/**
	 * use a different model that allows the color to be set to null
	 */
	@Override
	protected TestModel buildTestModel() {
		return new TestModel2();
	}

	/**
	 * add a null to the front of the list
	 */
	@Override
	protected ListValueModel<String> uiColorListHolder() {
		// the default is to prepend the wrapped list with a null item
		return new ExtendedListValueModelWrapper<String>(super.uiColorListHolder());
	}

	/**
	 * convert null to some text
	 */
	@Override
	protected ListCellRenderer buildComboBoxRenderer() {
		return new SimpleListCellRenderer() {
			@Override
			protected String buildText(Object value) {
				return (value == null) ? "<none selected>" : super.buildText(value);
			}
		};
	}


	protected static class TestModel2 extends TestModel {
		/**
		 * null is OK here
		 */
		@Override
		public void checkColor(String color) {
			if (color == null) {
				return;
			}
			super.checkColor(color);
		}
	}

}
