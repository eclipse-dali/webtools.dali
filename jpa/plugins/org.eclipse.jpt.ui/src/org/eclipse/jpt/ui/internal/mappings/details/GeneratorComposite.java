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
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jpt.core.internal.mappings.IGenerator;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * GeneratorComposite
 */
public abstract class GeneratorComposite<E extends IGenerator> extends BaseJpaComposite
{
	private IId id;
	private E generator;
	private Adapter generatorListener;

	protected ITextViewer nameViewer;

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
	protected ITextViewer buildNameViewer(Composite parent) {
		final TextViewer textViewer = new TextViewer(parent, SWT.SINGLE | SWT.BORDER);
		textViewer.setDocument(new Document());
		textViewer.addTextListener(new ITextListener() {
			public void textChanged(TextEvent event) {
				if (isPopulating()) {
					return;
				}
				
				String name = textViewer.getDocument().get();
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
		return textViewer;
	}

	protected abstract E createGenerator();

	@Override
	protected void doPopulate(EObject obj) {
		this.id = (IId) obj;
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

	protected abstract E generator(IId id);
	
	protected IId idMapping() {
		return this.id;
	}

	protected void generatorChanged(Notification notification) {
		if (notification.getFeatureID(IGenerator.class) == JpaCoreMappingsPackage.IGENERATOR__NAME) {
			final String name = notification.getNewStringValue();
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					if (nameViewer.getDocument().get() == null || !nameViewer.getDocument().get().equals(name)) {
						if (name == null) {
							clearNameViewer();
						}
						else {
							nameViewer.getDocument().set(name);
						}
					}
				}
			});
		}
	}

	private void populateNameViewer() {
		String name = this.getGenerator().getName();
		if (name != null) {
			if (!this.nameViewer.getDocument().get().equals(name)) {
				this.nameViewer.getDocument().set(name);
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
		this.nameViewer.getDocument().set(""); //$NON-NLS-1$
	}
	
	public void dispose() {
		disengageListeners();
		super.dispose();
	}
}
