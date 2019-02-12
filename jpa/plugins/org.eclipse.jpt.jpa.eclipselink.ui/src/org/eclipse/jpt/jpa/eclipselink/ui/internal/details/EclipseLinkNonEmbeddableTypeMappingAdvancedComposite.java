/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class EclipseLinkNonEmbeddableTypeMappingAdvancedComposite
	extends EclipseLinkTypeMappingAdvancedComposite<TypeMapping> {
	
	public EclipseLinkNonEmbeddableTypeMappingAdvancedComposite(
			Pane<? extends TypeMapping> parentPane,
			Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// read-only check box
		EclipseLinkReadOnlyTriStateCheckBox readOnlyCheckBox = new EclipseLinkReadOnlyTriStateCheckBox(this, buildReadOnlyHolder(), container);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		readOnlyCheckBox.getControl().setLayoutData(gridData);
		
		// customizer class chooser
		Hyperlink customizerHyperlink = addHyperlink(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CUSTOMIZER_COMPOSITE_CLASS_LABEL);
		new EclipseLinkCustomizerClassChooser(this, this.buildCustomizerHolder(), container, customizerHyperlink);

		// change tracking type
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CHANGE_TRACKING_COMPOSITE_LABEL); 
		new EclipseLinkChangeTrackingComboViewer(this, this.buildChangeTrackingHolder(), container);
	}
	
	private PropertyValueModel<EclipseLinkReadOnly> buildReadOnlyHolder() {
		return new PropertyAspectAdapter<TypeMapping, EclipseLinkReadOnly>(getSubjectHolder()) {
			@Override
			protected EclipseLinkReadOnly buildValue_() {
				return ((EclipseLinkNonEmbeddableTypeMapping) this.subject).getReadOnly();
			}
		};
	}
}
