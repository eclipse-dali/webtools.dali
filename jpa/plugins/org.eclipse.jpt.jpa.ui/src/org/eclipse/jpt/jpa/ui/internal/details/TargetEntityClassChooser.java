/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
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
	protected ModifiablePropertyValueModel<String> buildTextHolder() {
		return new PropertyAspectAdapter<RelationshipMapping, String>(
			this.getSubjectHolder(),
			RelationshipMapping.SPECIFIED_TARGET_ENTITY_PROPERTY,
			RelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getSpecifiedTargetEntity();
				if (name == null) {
					name = TargetEntityClassChooser.this.getDefaultValue(this.subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(this.subject).equals(value)) {
					value = null;
				}
				this.subject.setSpecifiedTargetEntity(value);
			}
		};
    }

	@Override
	protected ListValueModel<String> buildClassListHolder() {
		return this.buildDefaultProfilerListHolder();
	}

	private ListValueModel<String> buildDefaultProfilerListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultProfilerHolder()
		);
	}

	private PropertyValueModel<String> buildDefaultProfilerHolder() {
		return new PropertyAspectAdapter<RelationshipMapping, String>(this.getSubjectHolder(), RelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY) {
			@Override
			protected String buildValue_() {
				return TargetEntityClassChooser.this.getDefaultValue(this.subject);
			}
		};
	}

	private String getDefaultValue(RelationshipMapping subject) {
		String defaultValue = subject.getDefaultTargetEntity();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptCommonUiMessages.DEFAULT_EMPTY;
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
	 * @param page
	 * @param file
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
	 * @param page
	 * @param persistentType
	 */

	private void setSelectionInEditor(IWorkbenchPage page, PersistentType persistentType) {
		JpaSelectionManager selectionManager = PlatformTools.getAdapter(page, JpaSelectionManager.class);
		selectionManager.setSelection(persistentType);
	}
}