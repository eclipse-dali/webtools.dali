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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jpt.core.internal.context.base.IGenerator;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * GeneratorComposite
 */
@SuppressWarnings("nls")
public abstract class GeneratorComposite<E extends IGenerator> extends BaseJpaComposite<IIdMapping>
{
	private E generator;
	private Adapter generatorListener;
	private IIdMapping id;
	protected Text nameTextWidget;

	public GeneratorComposite(PropertyValueModel<? extends IIdMapping> subjectHolder,
	                          Composite parent,
	                          TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NULL, widgetFactory);
		this.generatorListener = buildGeneratorListner();
	}

	private Adapter buildGeneratorListner() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				generatorChanged(notification);
			}
		};
	}

	/**
	 * Builds the Generator specifiedName viewer.
	 *
	 * @param parent
	 * @return
	 */
	protected Text buildNameText(Composite parent) {
		final Text text = getWidgetFactory().createText(parent, null);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				if (isPopulating()) {
					return;
				}

				String name = text.getText();
				if (name.equals("")) {
					if (getGenerator().getName() == null) {
						return;
					}
					name = null;
				}
				IGenerator generator = getGenerator();
				if (generator == null) {
					generator = createGenerator();
				}
				generator.setName(name);
			}
		});
		return text;
	}

	protected void clear() {
		this.clearNameViewer();
	}

	protected void clearNameViewer() {
		this.nameTextWidget.setText("");
	}

	protected abstract E createGenerator();

	@Override
	protected void disengageListeners() {
		super.disengageListeners();
//		if (this.generator != null) {
//			this.generator.eAdapters().remove(this.generatorListener);
//		}
	}

	@Override
	protected void doPopulate() {
		if (this.id  == null) {
			this.generator = null;
			return;
		}
		this.generator = generator(this.id);
		if (this.generator == null) {
			clear();
			return;
		}
		populateNameViewer();
		return;
	}

	@Override
	protected void engageListeners() {
		super.engageListeners();
//		if (this.generator != null) {
//			this.generator.eAdapters().add(this.generatorListener);
//		}
	}

	protected abstract E generator(IIdMapping id);

	protected void generatorChanged(Notification notification) {
		if (notification.getFeatureID(IGenerator.class) == JpaCoreMappingsPackage.IGENERATOR__NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					if (nameTextWidget.getText() == null || !nameTextWidget.getText().equals(getGenerator().getName())) {
						if (getGenerator().getName() == null) {
							clearNameViewer();
						}
						else {
							nameTextWidget.setText(getGenerator().getName());
						}
					}
				}
			});
		}
	}

	protected E getGenerator() {
		return this.generator;
	}

	protected IIdMapping idMapping() {
		return this.id;
	}

	private void populateNameViewer() {
		String name = this.getGenerator().getName();
		if (name != null) {
			if (!this.nameTextWidget.getText().equals(name)) {
				this.nameTextWidget.setText(name);
			}
		}
		else {
			this.clearNameViewer();
		}
	}
}