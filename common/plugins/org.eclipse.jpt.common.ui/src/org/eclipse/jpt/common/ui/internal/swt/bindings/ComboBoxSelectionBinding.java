/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.internal.swt.events.ModifyAdapter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

/**
 * This binding can be used to keep a combo-box's text
 * synchronized with a model.
 * <p>
 * <strong>NB:</strong> This binding is bi-directional.
 * 
 * @see ModifiablePropertyValueModel
 * @see ComboBox
 * @see SWTBindingTools
 */
final class ComboBoxSelectionBinding
	extends AbstractComboSelectionBinding<String, ComboBoxSelectionBinding.ComboBox>
{
	/**
	 * A listener that allows us to synchronize our value model
	 * with the combo-box's text.
	 */
	private final ModifyListener comboBoxModifyListener;

	// ***** hack
	/**
	 * This flag is set by {@link ComboBoxListWidgetAdapter} whenever it is
	 * modifying the combo's list; preventing the binding from forwarding any
	 * changes to the combo's text field to the model. See the class comment
	 * at {@link ComboBoxListWidgetAdapter}.
	 */
	boolean modifyingComboList = false;


	/**
	 * Constructor - all parameters are required.
	 */
	ComboBoxSelectionBinding(
			ArrayList<String> list,
			ModifiablePropertyValueModel<String> valueModel,
			ComboBox comboBox
	) {
		super(list, valueModel, comboBox);

		this.comboBoxModifyListener = new ComboBoxModifyListener();
		this.combo.addModifyListener(this.comboBoxModifyListener);
	}

	/* CU private */ class ComboBoxModifyListener
		extends ModifyAdapter
	{
		@Override
		public void modifyText(ModifyEvent event) {
			ComboBoxSelectionBinding.this.comboBoxTextModified();
		}
	}

	/**
	 * The value of the combo-box's text field is not affected by the list of
	 * candidate values. Those values are simply suggestions, as opposed to the
	 * list used by a "drop-down list box", where the selection must come from
	 * the list.
	 */
	public void listChanged() {
		// NOP
	}

	@Override
	void valueChanged_(String text) {
		text = (text == null) ? StringTools.EMPTY_STRING : text;
		if ( ! text.equals(this.combo.getText())) {  // combo-box does not short-circuit
			this.combo.setText(text);
		}
	}

	/* CU private */ void comboBoxTextModified() {
		if ( ! this.modifyingComboList) {
			this.valueModel.setValue(this.combo.getText());
		}
	}

	@Override
	public void dispose() {
		this.combo.removeModifyListener(this.comboBoxModifyListener);
		super.dispose();
	}


	// ********** adapter interface **********

	/**
	 * Adapter used by the drop-down list box selection binding to query and manipulate
	 * the drop-down list box.
	 */
	interface ComboBox
		extends AbstractComboSelectionBinding.ComboAdapter
	{
		/**
		 * Add the specified modify listener to the combo-box.
		 */
		void addModifyListener(ModifyListener listener);

		/**
		 * Remove the specified modify listener from the combo-box.
		 */
		void removeModifyListener(ModifyListener listener);

		/**
		 * Return the combo-box's text.
		 */
		String getText();

		/**
		 * Set the combo-box's text.
		 */
		void setText(String text);
	}
}
