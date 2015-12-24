/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2012 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/

package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

/**
 * A new {@link Command} class that is called to create a new attribute in the
 * selected persistent type.
 * 
 * @author i045693
 * 
 */
public class AddAttributeCommand implements Command {

	private PersistentType jpt;
	private String attributeType;
	private String mapKeyType;
	private String attributeName;
	private String actName;
	private String[] attrTypes;
	private List<String> annotations;
	private boolean isCollection;

	/**
	 * Constructor for the create new attribute command.
	 * 
	 * @param jpt
	 * @param attributeType
	 * @param mapKeyType
	 * @param attributeName
	 * @param actName
	 * @param isCollection
	 */
	public AddAttributeCommand(
			PersistentType jpt, String attributeType, String mapKeyType,
			String attributeName, String actName, String[] attrTypes,
			List<String> annotations,
			boolean isCollection) {
		super();
		this.jpt = jpt;
		this.attributeType = attributeType;
		this.mapKeyType = mapKeyType;
		this.attributeName = attributeName;
		this.actName = actName;
		this.attrTypes = attrTypes;
		this.annotations = annotations;
		this.isCollection = isCollection;
	}

	/**
	 * Creates a new attribute.
	 */
	public void execute() {
		try {
			IJavaProject jp = JavaCore.create(jpt.getJpaProject().getProject());
			IType type = jp.findType(jpt.getName());
			ICompilationUnit cu = type.getCompilationUnit();
			JPAEditorUtil.createImport(cu, attributeType);
			attributeType = JPAEditorUtil.returnSimpleName(attributeType);
			if ((attrTypes != null) && (attrTypes.length > 0)) {
				JPAEditorUtil.createImports(cu, attrTypes);
			}

			String contents = ""; //$NON-NLS-1$
			if (annotations != null) {
				Iterator<String> it = annotations.iterator();
				while (it.hasNext()) {
					String an = it.next();
					contents += "   " + an + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			
			createAttribute(jpt, attributeType, mapKeyType, attributeName,
					actName, cu, type, isCollection, attrTypes, contents);

			jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();

		} catch (JavaModelException e) {
			JPADiagramEditorPlugin
					.logError(
							"Cannnot create a new attribute with name " + attributeName, e); //$NON-NLS-1$				
		}
	}

	/**
	 * Creates an attribute the persistent type.
	 * 
	 * @param jpt
	 * @param attrTypeName
	 * @param mapKeyType
	 * @param attrName
	 * @param actName
	 * @param cu
	 * @param type
	 * @param isCollection
	 * @throws JavaModelException
	 */
	private void createAttribute(PersistentType jpt, String attrTypeName, String mapKeyType,
			String attrName, String actName, ICompilationUnit cu, IType type,
			boolean isCollection, String[] attrTypeElementNames, String annotationContents) throws JavaModelException {

			if (isCollection) {
				createAttributeOfCollectiontype(jpt, attrTypeName,
						mapKeyType, attrName, actName, cu, type);
			} else {
				createSimpleAttribute(attrTypeName, attrName, actName,
						isCollection, type, attrTypeElementNames, annotationContents);
			}
	}

	/**
	 * Creates a new attribute of a basic type.
	 * 
	 * @param attributeType
	 * @param attributeName
	 * @param actName
	 * @param isCollection
	 * @param type
	 * @throws JavaModelException
	 */
	private void createSimpleAttribute(String attributeType,
			String attributeName, String actName, boolean isCollection,
			IType type, String[] attrTypeElementNames, String annotationContents) throws JavaModelException {
		
		
		String attrFieldContent = "    private " + attributeType + //$NON-NLS-1$
				((attrTypes == null) ? "" : ("<" + JPAEditorUtil.createCommaSeparatedListOfSimpleTypeNames(attrTypes) + ">")) + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				" " + JPAEditorUtil.decapitalizeFirstLetter(actName) + ";"; //$NON-NLS-1$ //$NON-NLS-2$
		
		String contents = ""; //$NON-NLS-1$
		if(jpt!= null && !JpaArtifactFactory.instance().isMethodAnnotated(jpt)){
			contents = annotationContents + attrFieldContent;
		} else {
			contents = attrFieldContent;
		}

		type.createField(contents, null, false, new NullProgressMonitor());
		
		type.createMethod(
				genGetterContents(attributeName, attributeType, actName, attrTypeElementNames, annotationContents), null,
				false, new NullProgressMonitor());
		type.createMethod(
				genSetterContents(attributeName, attributeType, actName, attrTypeElementNames), null,
				false, new NullProgressMonitor());
	}

	/**
	 * Creates a new attribute of a collection type, depending on the specified
	 * collection type in the Preference/Properties page.
	 * 
	 * @param jpt
	 * @param attributeType
	 * @param mapKeyType
	 * @param attributeName
	 * @param actName
	 * @param cu
	 * @param type
	 * @throws JavaModelException
	 */
	private void createAttributeOfCollectiontype(
			PersistentType jpt, String attributeType, String mapKeyType,
			String attributeName, String actName, ICompilationUnit cu,
			IType type) throws JavaModelException {
		IProject project = jpt.getJpaProject().getProject();
		Properties props = loadProperties(project);
		if (JPADiagramPropertyPage.isCollectionType(project, props)) {
			createAttributeByCollectionMethodType(attributeType, null,
					attributeName, actName, cu, type,
					JPAEditorConstants.COLLECTION_TYPE);
		} else if (JPADiagramPropertyPage.isListType(project, props)) {
			createAttributeByCollectionMethodType(attributeType, null,
					attributeName, actName, cu, type,
					JPAEditorConstants.LIST_TYPE);
		} else if (JPADiagramPropertyPage.isSetType(project, props)) {
			createAttributeByCollectionMethodType(attributeType, null,
					attributeName, actName, cu, type,
					JPAEditorConstants.SET_TYPE);
		} else {
			createAttributeByCollectionMethodType(attributeType, mapKeyType,
					attributeName, actName, cu, type,
					JPAEditorConstants.MAP_TYPE);
		}
	}

	/**
	 * Create attribute by the specified collection type in the
	 * Preference/Properties page.
	 * 
	 * @param attributeType
	 * @param mapKeyType
	 * @param attributeName
	 * @param actName
	 * @param cu
	 * @param type
	 * @param collectionType
	 * @throws JavaModelException
	 */
	private void createAttributeByCollectionMethodType(String attributeType,
			String mapKeyType, String attributeName, String actName,
			ICompilationUnit cu, IType type, String collectionType)
			throws JavaModelException {
		mapKeyType = createContentType(mapKeyType, attributeType, actName, cu,
				type, collectionType);
		type.createMethod(
				genGetterWithAppropriateType(attributeName, mapKeyType,
						attributeType, actName, collectionType), null, false,
				new NullProgressMonitor());
		type.createMethod(
				genSetterWithAppropriateType(attributeName, mapKeyType,
						attributeType, actName, collectionType), null, false,
				new NullProgressMonitor());
	}

	/**
	 * Create field in the entity by the specified collection type.
	 * 
	 * @param mapKeyType
	 * @param attributeType
	 * @param actName
	 * @param cu
	 * @param type
	 * @param collectionType
	 * @return string representation of the field's collection type.
	 * @throws JavaModelException
	 */
	private String createContentType(String mapKeyType, String attributeType,
			String actName, ICompilationUnit cu, IType type,
			String collectionType) throws JavaModelException {

		if (mapKeyType != null) {
			mapKeyType = JPAEditorUtil.createImport(cu, mapKeyType);
		}
		JPAEditorUtil.createImport(cu, collectionType);
		type.createField(
				"  private " + JPAEditorUtil.returnSimpleName(collectionType) + "<" + //$NON-NLS-1$ //$NON-NLS-2$
						((mapKeyType != null) ? (mapKeyType + ", ") : "") + //$NON-NLS-1$ //$NON-NLS-2$
						attributeType
						+ "> " + JPAEditorUtil.decapitalizeFirstLetter(actName) + //$NON-NLS-1$
						";", null, false, new NullProgressMonitor()); //$NON-NLS-1$ 
		return mapKeyType;
	}

	/**
	 * Create the attribute's getter method in entity's compilation unit with
	 * the appropriate collection type, selected in the Preference/Properties
	 * page.
	 * 
	 * @param attrName
	 *            - the name of the attribute
	 * @param attrType
	 *            - the type of the attribute
	 * @param attrTypeElementNames
	 * @param actName
	 * @param isCollection
	 * @return the string representation of the attribute's getter method.
	 */
	private String genGetterWithAppropriateType(String attrName,
			String mapKeyType, String attrType, String actName, String type) {

		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(
				Locale.ENGLISH)
				+ actName.substring(1);
		String contents = "    public " + JPAEditorUtil.returnSimpleName(type) + //$NON-NLS-1$
				"<" //$NON-NLS-1$
				+ ((mapKeyType != null) ? (mapKeyType + ", ") : "") + attrType + "> " + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"get" + attrNameWithCapitalA + "() {\n" + //$NON-NLS-1$ //$NON-NLS-2$
				"        return " //$NON-NLS-1$
				+ JPAEditorUtil.decapitalizeFirstLetter(actName) + ";\n" + //$NON-NLS-1$
				"    }\n"; //$NON-NLS-1$
		return contents;
	}

	/**
	 * Create the attribute's setter method in entity's compilation unit with
	 * the appropriate collection type, selected in the Preference/Properties
	 * page.
	 * 
	 * @param attrName
	 *            - the name of the attribute
	 * @param attrType
	 *            - the type of the attribute
	 * @param attrTypeElementNames
	 * @param actName
	 * @param isCollection
	 * @return the string representation of the attribute's setter method.
	 */
	private String genSetterWithAppropriateType(String attrName,
			String mapKeyType, String attrType, String actName, String type) {

		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(
				Locale.ENGLISH)
				+ actName.substring(1);
		String contents = "    public void set" + attrNameWithCapitalA + //$NON-NLS-1$
				"(" //$NON-NLS-1$
				+ JPAEditorUtil.returnSimpleName(type)
				+ 
				"<" //$NON-NLS-1$
				+ ((mapKeyType != null) ? (mapKeyType + ", ") : "") + attrType + "> param) " + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"{\n" //$NON-NLS-1$
				+ 
				"        this." //$NON-NLS-1$
				+ JPAEditorUtil.decapitalizeFirstLetter(actName)
				+ " = param;\n" + //$NON-NLS-1$
				"    }\n"; //$NON-NLS-1$
		return contents;
	}

	/**
	 * Create the attribute's getter method in entity's compilation unit.
	 * 
	 * @param attrName
	 *            - the name of the attribute
	 * @param attrType
	 *            - the type of the attribute
	 * @param actName
	 * @param annotationContents - the annotations as string representation
	 * @return the string representation of the attribute's getter method.
	 */
	private String genGetterContents(String attrName, String attrType,
			String actName, String[] attrTypeElementNames, String annotationContents) {
		
		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(
				Locale.ENGLISH)
				+ actName.substring(1);
		
		String contents = ""; //$NON-NLS-1$
		if(jpt != null && JpaArtifactFactory.instance().isMethodAnnotated(jpt)){
			contents += annotationContents;
		}
		contents += "    public " + attrType + //$NON-NLS-1$
				((attrTypeElementNames == null) ? "" : ("<" + JPAEditorUtil.createCommaSeparatedListOfSimpleTypeNames(attrTypeElementNames) + ">")) + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				(attrType.equals("boolean") ? " is" : " get") + attrNameWithCapitalA + "() {\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ 
				"        return " //$NON-NLS-1$
				+ JPAEditorUtil.decapitalizeFirstLetter(actName) + ";\n" + //$NON-NLS-1$ 
				"    }\n"; //$NON-NLS-1$

		return contents;
	}

	/**
	 * Create the attribute's setter method in entity's compilation unit.
	 * 
	 * @param attrName
	 *            - the name of the attribute
	 * @param attrType
	 *            - the type of the attribute
	 * @return the string representation of the attribute's setter method.
	 */
	private String genSetterContents(String attrName, String attrType,
			String actName, String[] attrTypeElementNames) {
		
		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(
				Locale.ENGLISH) + actName.substring(1);

		String contents = "    public void set" + attrNameWithCapitalA + "(" + attrType + //$NON-NLS-1$ //$NON-NLS-2$
				((attrTypeElementNames == null) ? "" : ("<" + JPAEditorUtil.createCommaSeparatedListOfSimpleTypeNames(attrTypeElementNames) + ">")) + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				" param) {\n" //$NON-NLS-1$
				+ "        this." //$NON-NLS-1$
				+ JPAEditorUtil.decapitalizeFirstLetter(actName)
				+ " = param;\n" + //$NON-NLS-1$ 
				"    }\n"; //$NON-NLS-1$
		return contents;
	}
	
	private Properties loadProperties(IProject project) {
		return JPADiagramPropertyPage.loadProperties(project);
	}

}
