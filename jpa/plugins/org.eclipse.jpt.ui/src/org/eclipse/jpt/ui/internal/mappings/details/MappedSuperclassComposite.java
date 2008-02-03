/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IMappedSuperclass;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @see IMappedSuperclass
 * @see BaseJpaUiFactory - The factory creating this pane
 *
 * @version 2.0
 * @since 2.0
 */
public class MappedSuperclassComposite extends AbstractFormPane<IMappedSuperclass>
                                       implements IJpaComposite<IMappedSuperclass>
{
	/**
	 * Creates a new <code>MappedSuperclassComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public MappedSuperclassComposite(PropertyValueModel<? extends IMappedSuperclass> subjectHolder,
	                                 Composite parent,
	                                 TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}


//	private ComboViewer buildAccessTypeCombo(Composite parent) {
//		ComboViewer viewer = new ComboViewer(parent, SWT.READ_ONLY);
//		viewer.add(AccessType.VALUES.toArray());
//		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
//			public void selectionChanged(SelectionChangedEvent event) {
//				if (populating) {
//					return;
//				}
//				if (event.getSelection() instanceof StructuredSelection) {
//					StructuredSelection selection = (StructuredSelection) event.getSelection();
//					editingDomain.getCommandStack().execute(SetCommand.create(editingDomain, persistentType, OrmPackage.eINSTANCE.getPersistentType_AccessType(), selection.getFirstElement()));
//				}
//			}
//		});
//		return viewer;
//	}


	@Override
	protected void initializeLayout(Composite composite) {

//		Label accessTypeLabel = new Label(composite, SWT.LEFT);
//		accessTypeLabel.setText(DaliUiMessages.MappedSuperclassComposite_accessType);
//		this.accessTypeComboViewer = buildAccessTypeCombo(composite);
//		//eventually this will be enabled if editing xml instead of java
//		this.accessTypeComboViewer.getCombo().setEnabled(false);
//		GridData gridData = new GridData();
//		gridData.horizontalAlignment = GridData.FILL;
//		gridData.grabExcessHorizontalSpace = true;
//		this.accessTypeComboViewer.getCombo().setLayoutData(gridData);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.accessTypeComboViewer.getCombo(), IDaliHelpContextIds.ENTITY_ACCESS_TYPE);

	}
}