/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
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
	@SuppressWarnings("unused")
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
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> ((EclipseLinkNonEmbeddableTypeMapping) m).getReadOnly());
	}
}
