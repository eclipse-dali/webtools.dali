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

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.mappings.IGenerator;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * GeneratorComposite
 */
public abstract class GeneratorComposite<E extends IGenerator> extends BaseJpaComposite
{
	private IIdMapping id;
	private E generator;
	private Adapter generatorListener;

	protected Text nameTextWidget;

	public GeneratorComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
		this.generatorListener = buildGeneratorListner();
	}
	
	private Adapter buildGeneratorListner() {
		return new AdapterImpl() {
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
				if (name.equals("")) { //$NON-NLS-1$
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

	protected abstract E createGenerator();

	@Override
	protected void doPopulate(EObject obj) {
		this.id = (IIdMapping) obj;
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
	protected void doPopulate() {
		populateNameViewer();
	}
	
	protected void engageListeners() {
		if (this.generator != null) {
			this.generator.eAdapters().add(this.generatorListener);
		}
	}

	protected void disengageListeners() {
		if (this.generator != null) {
			this.generator.eAdapters().remove(this.generatorListener);
		}
	}

	protected abstract E generator(IIdMapping id);
	
	protected IIdMapping idMapping() {
		return this.id;
	}

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

	protected E getGenerator() {
		return this.generator;
	}

	protected void clear() {
		this.clearNameViewer();
	}

	protected void clearNameViewer() {
		this.nameTextWidget.setText(""); //$NON-NLS-1$
	}
}
