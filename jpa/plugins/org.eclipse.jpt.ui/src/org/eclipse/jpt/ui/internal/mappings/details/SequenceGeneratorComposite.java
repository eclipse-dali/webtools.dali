/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * SequenceGeneratorComposite
 */
public class SequenceGeneratorComposite extends GeneratorComposite<ISequenceGenerator>
{
	private ConnectionProfile connectionProfile;
	private CCombo sequenceNameCombo;
	private ModifyListener sequenceNameComboListener;

	public SequenceGeneratorComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}

	private CCombo buildSequenceNameCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		combo.add(""); //$NON-NLS-1$
		combo.select(0);
		combo.addModifyListener(getSequenceNameListener());
		return combo;
	}

	@Override
	protected void clear() {
		super.clear();
		this.sequenceNameCombo.select(0);
	}

	@Override
	protected ISequenceGenerator createGenerator() {
		ISequenceGenerator sequenceGenerator = idMapping().createSequenceGenerator();
		idMapping().setSequenceGenerator(sequenceGenerator);
		return sequenceGenerator;
	}

	@Override
	protected void doPopulate() {
		populateSequenceNameCombo();
	}

	@Override
	protected ISequenceGenerator generator(IIdMapping idMapping) {
		return idMapping.getSequenceGenerator();
	}

	@Override
	protected void generatorChanged(Notification notification) {
		super.generatorChanged(notification);
		if (notification.getFeatureID(ISequenceGenerator.class) == JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					String sequenceName = getGenerator().getSpecifiedSequenceName();
					if (sequenceName == null) {
						sequenceNameCombo.select(0);
					}
					else if (!sequenceNameCombo.getText().equals(sequenceName)) {
						sequenceNameCombo.setText(sequenceName);
					}
				}
			});
		}
	}

	private ConnectionProfile getConnectionProfile() {
		if(this.connectionProfile == null) {
			IJpaProject jpaProject = getGenerator().getJpaProject();
			this.connectionProfile = jpaProject.connectionProfile();
		}
		return this.connectionProfile;
	}

	private ModifyListener getSequenceNameListener() {
		if (this.sequenceNameComboListener == null) {
			this.sequenceNameComboListener = new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					if (isPopulating()) {
						return;
					}
					String text = ((CCombo) e.getSource()).getText();
					if (text != null && sequenceNameCombo.getItemCount() > 0 && text.equals(sequenceNameCombo.getItem(0))) {
						text = null;
					}
					ISequenceGenerator generator = getGenerator();
					if (generator == null) {
						generator = createGenerator();
					}
					generator.setSpecifiedSequenceName(text);
				}
			};
		}
		return this.sequenceNameComboListener;
	}

	@Override
	protected void initializeLayout(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.SequenceGeneratorComposite_name);

		this.nameTextWidget = buildNameText(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.nameTextWidget.setLayoutData(gridData);
		helpSystem.setHelp(this.nameTextWidget, IJpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME);

		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.SequenceGeneratorComposite_sequence);

		this.sequenceNameCombo = buildSequenceNameCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.sequenceNameCombo.setLayoutData(gridData);
		helpSystem.setHelp(sequenceNameCombo, IJpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE);
	}

	private void populateSequenceNameCombo() {
		if (this.getGenerator() == null) {
			return;
		}
		this.sequenceNameCombo.setItem(0, JptUiMappingsMessages.SequenceGeneratorComposite_default);
		if (this.getConnectionProfile().isConnected()) {
//			this.sequenceNameCombo.remove(1, this.sequenceNameCombo.getItemCount() - 1);
//			Schema schema = getConnectionProfile().getDatabase().schemaNamed(getGenerator().getJpaProject().getSchemaName());
//			if (schema != null) {
//				for (Iterator stream = CollectionTools.sort(schema.sequenceNames()); stream.hasNext();) {
//					this.sequenceNameCombo.add((String) stream.next());
//				}
//			}
		}
		String sequenceName = this.getGenerator().getSpecifiedSequenceName();
		if (sequenceName != null) {
			if (!this.sequenceNameCombo.getText().equals(sequenceName)) {
				this.sequenceNameCombo.setText(sequenceName);
			}
		}
		else {
			this.sequenceNameCombo.select(0);
		}
	}
}