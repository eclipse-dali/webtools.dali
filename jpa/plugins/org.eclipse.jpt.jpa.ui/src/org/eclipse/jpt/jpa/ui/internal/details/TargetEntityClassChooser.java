/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.ide.IDE;

/**
 *  target entity hyperlink label, combo and browse button 
 */
public class TargetEntityClassChooser
	extends ClassChooserComboPane<RelationshipMapping>
{
	/**
	 * Creates a new <code>TargetEntityComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TargetEntityClassChooser(
			Pane<? extends RelationshipMapping> parentPane,
	        Composite parent,
	        Hyperlink hyperlink) {
		
		super(parentPane, parent, hyperlink);
	}

	@Override
	protected String getClassName() {
		return getSubject().getTargetEntity();
	}

	@Override
	protected void setClassName(String className) {
		this.getSubject().setSpecifiedTargetEntity(className);
	}

    @Override
    protected String getHelpId() {
    	return JpaHelpContextIds.MAPPING_TARGET_ENTITY;
    }

	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

    @Override
    protected char getEnclosingTypeSeparator() {
    	return getSubject().getTargetEntityEnclosingTypeSeparator();
    }

    @Override
    protected String getFullyQualifiedClassName() {
    	return getSubject().getFullyQualifiedTargetEntity();
    }

    @Override
	protected ModifiablePropertyValueModel<String> buildTextModel() {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter(
				this.getSubjectHolder(),
				RelationshipMapping.TARGET_ENTITY_PROPERTY,
				m -> (m.getSpecifiedTargetEntity() == null) ? this.defaultValue(m) : m.getSpecifiedTargetEntity(),
				(m, value) -> m.setSpecifiedTargetEntity(defaultValue(m).equals(value) ? null : value)
			);
    }

	@Override
	protected ListValueModel<String> buildClassListModel() {
		return this.buildDefaultProfilerListModel();
	}

	private ListValueModel<String> buildDefaultProfilerListModel() {
		return new PropertyListValueModelAdapter<>(
			this.buildDefaultProfilerModel()
		);
	}

	private PropertyValueModel<String> buildDefaultProfilerModel() {
		return new PropertyAspectAdapterXXXX<RelationshipMapping, String>(this.getSubjectHolder(), RelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY) {
			@Override
			protected String buildValue_() {
				return TargetEntityClassChooser.this.defaultValue(this.subject);
			}
		};
	}

	String defaultValue(RelationshipMapping subject) {
		String defaultValue = subject.getDefaultTargetEntity();
		return (defaultValue == null) ?
				JptCommonUiMessages.DEFAULT_EMPTY :
				NLS.bind(JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM, defaultValue);
	}
	
	/*
	 * Duplicate persistent type case is not handled so the opened mapping file could be 
	 * any one of the existent mapping files that has the persistent type.
	 * User needs to solve the duplicate problem to open the mapping file he wants to open.
	 */
	@Override
	protected void hyperLinkSelected() {
		String className = this.getFullyQualifiedClassName();
		if (className != null) {
			PersistentType pType = this.getSubject().getPersistenceUnit().getPersistentType(className);
			if (pType instanceof OrmPersistentType) {
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IFile file = ((OrmPersistentType)pType).getParent().getParent().getXmlResource().getFile();
					openInEditor(page, file);
					setSelectionInEditor(page, pType);
			} else {
				IType type = JavaProjectTools.findType(this.getJavaProject(), className);
				if (type != null) {
					openInEditor(type);
				}	else {
					createType();
				}
			}
		}
	}

	/**
	 * Open the file in an editor of the active workbench page
	 */
	private void openInEditor(IWorkbenchPage page, IFile file) {
		try {
			IDE.openEditor(page, file);
		} catch (PartInitException ex) {
			JptJpaUiPlugin.instance().logError(ex);
		}
	}

	/**
	 * Set the selection to the persistent type in the editor
	 */
	private void setSelectionInEditor(IWorkbenchPage page, PersistentType persistentType) {
		JpaSelectionManager selectionManager = PlatformTools.getAdapter(page, JpaSelectionManager.class);
		selectionManager.setSelection(persistentType);
	}
}
