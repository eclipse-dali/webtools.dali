package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class CreateNewAttributeCommand implements Command {
	
	private JavaPersistentType jpt;
	private ICompilationUnit cu;
	private String attrName;
	private String attrTypeName;
	private String[] attrTypes;
	private String actName;
	private List<String> annotations;
	private boolean isCollection;
	private boolean isMethodAnnotated;
	
	public CreateNewAttributeCommand(JavaPersistentType jpt, 
		ICompilationUnit cu, String attrName, String attrTypeName,
		String[] attrTypes, String actName,
		List<String> annotations, boolean isCollection,
		boolean isMethodAnnotated){
		super();
		this.jpt = jpt;
		this.cu = cu;
		this.attrName = attrName;
		this.attrTypeName = attrTypeName;
		this.attrTypes = attrTypes;
		this.actName = actName;
		this.annotations = annotations;
		this.isCollection = isCollection;
		this.isMethodAnnotated = isMethodAnnotated;
	}

	public void execute() {
		try {
		IType type = cu.findPrimaryType();
		String contents = ""; 														//$NON-NLS-1$
		isMethodAnnotated = (annotations != null) && (!annotations.isEmpty()) ? isMethodAnnotated
				: JpaArtifactFactory.instance().isMethodAnnotated(jpt);
		
		if (!isMethodAnnotated) {
			if (annotations != null) {
				Iterator<String> it = annotations.iterator();
				while (it.hasNext()) {
					String an = it.next();
					contents += "   " + an + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
				}							
			}
		}
		
		if(annotations!=null && annotations.contains("@Basic")){ //$NON-NLS-1$
			if(!cu.getImport("javax.persistence.*").exists() && !cu.getImport("javax.persistence.Basic").exists()){ //$NON-NLS-1$ //$NON-NLS-2$
				JPAEditorUtil.createImports(cu, "javax.persistence.Basic"); //$NON-NLS-1$
			}
		}
		
		boolean shouldAddImport = true;
		IImportDeclaration[] importDeclarations = cu.getImports();
		String attrShortTypeName = JPAEditorUtil.returnSimpleName(attrTypeName);
		for(IImportDeclaration importDecl : importDeclarations){
			String importedDeclarationFQN = importDecl.getElementName();
			String importedDeclarationShortName = JPAEditorUtil.returnSimpleName(importedDeclarationFQN);
			if(attrShortTypeName.equals(importedDeclarationShortName) && !attrTypeName.equals(importedDeclarationFQN))
				shouldAddImport = false;
		}
		
		if(shouldAddImport){
			JPAEditorUtil.createImports(cu, attrTypeName);
		    attrTypeName = JPAEditorUtil.returnSimpleName(attrTypeName);
		}
		if ((attrTypes != null) && (attrTypes.length > 0)) {
			JPAEditorUtil.createImports(cu, attrTypes);
		}
		
		contents += "    private " + attrTypeName + //$NON-NLS-1$
				((attrTypes == null) ? "" : ("<" + JPAEditorUtil.createCommaSeparatedListOfSimpleTypeNames(attrTypes) + ">")) + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				" " + attrName + ";"; //$NON-NLS-1$ //$NON-NLS-2$		

		type.createMethod(
				JpaArtifactFactory.instance().genSetterContents(attrName, attrTypeName, attrTypes,
						actName, isCollection), null, false,
				new NullProgressMonitor());
		if (isMethodAnnotated) {
			type.createMethod(
					JpaArtifactFactory.instance().genGetterContents(attrName, attrTypeName,
							attrTypes, actName, annotations,
							isCollection), null, false,
					new NullProgressMonitor());
			type.createField(contents, null, false, new NullProgressMonitor());
		} else {
			type.createField(contents, null, false, new NullProgressMonitor());
			type.createMethod(
					JpaArtifactFactory.instance().genGetterContents(attrName, attrTypeName,
							attrTypes, actName, null, isCollection),
					null, false, new NullProgressMonitor());
		}
		
	} catch (JavaModelException e) {
		
	}
		
		JavaPersistentAttribute attr  = jpt.getAttributeNamed(attrName);
		int cnt = 0;
		while ((attr == null) && (cnt < 25)) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
			jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
			attr = jpt.getAttributeNamed(attrName);
			cnt++;
		}
	}
}
