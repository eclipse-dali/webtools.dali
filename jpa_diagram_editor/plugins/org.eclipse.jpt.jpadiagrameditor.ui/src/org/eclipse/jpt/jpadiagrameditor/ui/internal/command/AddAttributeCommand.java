package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import java.util.Locale;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class AddAttributeCommand implements Command{

	private IJPAEditorFeatureProvider fp;
	private JavaPersistentType jpt;
	private JavaPersistentType attributeType;
	private String mapKeyType;
	private String attributeName;
	private String actName;
	private boolean isCollection;
	private ICompilationUnit cu1;
	private ICompilationUnit cu2;
	
	public AddAttributeCommand(IJPAEditorFeatureProvider fp, JavaPersistentType jpt, 
			JavaPersistentType attributeType, String mapKeyType, String attributeName,
			String actName, boolean isCollection, ICompilationUnit cu1,
			ICompilationUnit cu2){
		super();
		this.fp = fp;
		this.jpt = jpt;
		this.attributeType = attributeType;
		this.mapKeyType = mapKeyType;
		this.attributeName = attributeName;
		this.actName = actName;
		this.isCollection = isCollection;
		this.cu1 = cu1;
		this.cu2 = cu2;
	}

	public void execute() {
		IType type = null;
		try {
			JPAEditorUtil.createImport(this.cu1, this.cu2.getType(this.attributeType.getName()).getElementName());
			type = this.cu1.findPrimaryType();	

			if (this.isCollection) {
				createAttributeOfCollectiontype(this.fp, this.jpt, this.attributeType,
						this.mapKeyType, this.attributeName, this.actName, this.cu1, type);
			} else {
				createSimpleAttribute(this.attributeType, this.attributeName, this.actName,
						this.isCollection, type);
			}
								
			this.jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
			
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannnot create a new attribute with name " + attributeName, e); //$NON-NLS-1$				
		}
	}
	
	private void createSimpleAttribute(JavaPersistentType attributeType,
			String attributeName, String actName, boolean isCollection,
			IType type) throws JavaModelException {
		type.createField("  private " + JPAEditorUtil.returnSimpleName(attributeType.getName()) + " "
			+ JPAEditorUtil.decapitalizeFirstLetter(actName) + ";", null, false, new NullProgressMonitor()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		type.createMethod(JpaArtifactFactory.instance().genGetterContents(attributeName,
				JPAEditorUtil.returnSimpleName(attributeType.getName()), null,
				actName, null, isCollection), null, false,
				new NullProgressMonitor());
		type.createMethod(JpaArtifactFactory.instance().genSetterContents(attributeName,
				JPAEditorUtil.returnSimpleName(attributeType.getName()), null,
				actName, isCollection), null, false,
				new NullProgressMonitor());
	}

	private void createAttributeOfCollectiontype(IJPAEditorFeatureProvider fp,
			JavaPersistentType jpt, JavaPersistentType attributeType,
			String mapKeyType, String attributeName, String actName,
			ICompilationUnit cu1, IType type) throws JavaModelException {
		IProject project = jpt.getJpaProject().getProject();
		Properties props = fp.loadProperties(project);
		if (JPADiagramPropertyPage.isCollectionType(project, props)) {
			createAttributeByCollectionMethodType(attributeType, null,
					attributeName, actName, cu1, type, JPAEditorConstants.COLLECTION_TYPE);
		} else if (JPADiagramPropertyPage.isListType(project, props)) {
			createAttributeByCollectionMethodType(attributeType, null,
					attributeName, actName, cu1, type, JPAEditorConstants.LIST_TYPE);
		} else if (JPADiagramPropertyPage.isSetType(project, props)) {
			createAttributeByCollectionMethodType(attributeType, null,
					attributeName, actName, cu1, type, JPAEditorConstants.SET_TYPE);
		} else {
			createAttributeByCollectionMethodType(attributeType, mapKeyType,
					attributeName, actName, cu1, type, JPAEditorConstants.MAP_TYPE);
		}
	}

	
	private void createAttributeByCollectionMethodType(
			JavaPersistentType attributeType,  String mapKeyType, String attributeName,
			String actName, ICompilationUnit cu1, IType type, String collectionType)
			throws JavaModelException {
		mapKeyType = createContentType(mapKeyType, attributeType, actName, cu1, type, collectionType);
		type.createMethod(genGetterWithAppropriateType(attributeName, mapKeyType,
				JPAEditorUtil.returnSimpleName(attributeType.getName()), 
				actName, collectionType), null, false,
				new NullProgressMonitor());
		type.createMethod(genSetterWithAppropriateType(attributeName, mapKeyType,
				JPAEditorUtil.returnSimpleName(attributeType.getName()), 
				actName, collectionType), null, false,
				new NullProgressMonitor());
	}
	
	private String createContentType(String mapKeyType, JavaPersistentType attributeType,
			String actName, ICompilationUnit cu1, IType type, String collectionType)
			throws JavaModelException {
		
		if (mapKeyType != null) {
			mapKeyType = JPAEditorUtil.createImport(cu1, mapKeyType); 
		}
		JPAEditorUtil.createImport(cu1, collectionType);
		type.createField(
				"  private " + JPAEditorUtil.returnSimpleName(collectionType) + "<" +//$NON-NLS-1$ //$NON-NLS-2$
				((mapKeyType != null) ? (mapKeyType + ", ") : "") +			//$NON-NLS-1$ //$NON-NLS-2$
				JPAEditorUtil.returnSimpleName(attributeType.getName()) + "> " + JPAEditorUtil.decapitalizeFirstLetter(actName) +  //$NON-NLS-1$
				";", null, false, new NullProgressMonitor()); //$NON-NLS-1$ 
		return mapKeyType;
	}
	
	private String genGetterWithAppropriateType(String attrName, String mapKeyType, String attrType,
			String actName, String type) {

		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(
				Locale.ENGLISH)
				+ actName.substring(1);
		String contents = "    public " + JPAEditorUtil.returnSimpleName(type) + 		//$NON-NLS-1$
				"<" + ((mapKeyType != null) ? (mapKeyType + ", ") : "")  +  attrType + "> " +	//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"get" + attrNameWithCapitalA + "() {\n" + 	//$NON-NLS-1$ //$NON-NLS-2$
				"        return " 	//$NON-NLS-1$
				+ JPAEditorUtil.decapitalizeFirstLetter(actName) + ";\n" + 		//$NON-NLS-1$
				"    }\n"; 	//$NON-NLS-1$
		return contents;
	}
	
	private String genSetterWithAppropriateType(String attrName, String mapKeyType, String attrType,
			String actName, String type) {

		String attrNameWithCapitalA = actName.substring(0, 1).toUpperCase(
				Locale.ENGLISH)
				+ actName.substring(1);
		String contents = "    public void set" + attrNameWithCapitalA + 			//$NON-NLS-1$
				"(" + JPAEditorUtil.returnSimpleName(type) + 						//$NON-NLS-1$
				"<" + ((mapKeyType != null) ? (mapKeyType + ", ") : "") + attrType + "> param) " +	//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"{\n" +   	//$NON-NLS-1$
				"        this." 	//$NON-NLS-1$
				+ JPAEditorUtil.decapitalizeFirstLetter(actName)
				+ " = param;\n" + 	//$NON-NLS-1$
				"    }\n"; 			//$NON-NLS-1$
		return contents;
	}
	
}
