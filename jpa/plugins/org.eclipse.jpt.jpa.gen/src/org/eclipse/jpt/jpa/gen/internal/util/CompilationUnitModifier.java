/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.gen.internal.util;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;


/**
 * Performs the grunt work needed for modifying a compilation 
 * unit and performs the modified compilation unit save.
 * 
 * The typical usage is as follows:
 * <ol><li>Create an instance.
 * 
 * <li>Modify the compilation unit using AST operations performed 
 * 	on the node returned by {@link #getCompilationUnitNode()}.
 *  Alternatively you can call <code>setJavaSource</code> to change the entire source code. 
 * 
 * <li>Call the {@link #save()} method.
 * 
 */
public class CompilationUnitModifier
{
	private IJavaProject mProject;
	private ICompilationUnit mCompilationUnit;
	private CompilationUnit mCompilationUnitNode;
	private Document mDocument;
	
	public CompilationUnitModifier(IJavaProject project, String className) throws Exception {
		super();
		
		mProject = project;
		
		IType type = project.findType(className);
		if (type == null) {
			throw new Exception("The class " + className + " does not exist.");
		}
		mCompilationUnit = type.getCompilationUnit();
		if (mCompilationUnit == null) {
			throw new Exception("The source code for " + className + " does not exist.");
		}
	}
	public CompilationUnitModifier(IJavaProject project, ICompilationUnit cu) throws Exception {
		super();
		
		mProject = project;
		mCompilationUnit = cu;
	}
	public CompilationUnitModifier(IJavaProject project, ICompilationUnit cu, CompilationUnit cuNode) throws Exception {
		super();
		
		mProject = project;
		mCompilationUnit = cu;
		mCompilationUnitNode = cuNode;
		
		getCompilationUnitNode(); //to create mDocument (the caller in this case does not have to call getCompilationUnitNode)
	}
	public ICompilationUnit getCompilationUnit() {
		return mCompilationUnit;
	}
	/**
	 * Returns the compilation unit node that should be used for 
	 * tyhe modification AST operations.
	 */
	public CompilationUnit getCompilationUnitNode() {
		if (mCompilationUnitNode == null) {
			ASTParser c = ASTParser.newParser(AST.JLS3);
			c.setSource(mCompilationUnit);
			c.setResolveBindings(true);
			mCompilationUnitNode = (CompilationUnit)c.createAST(null);
		}
		if (mDocument == null) {
			try {
				mDocument = new Document(mCompilationUnit.getBuffer().getContents());
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
			
			mCompilationUnitNode.recordModifications();
		}
		
		return mCompilationUnitNode;
	}
	/**
	 * Changes the entire Java source code of the compilation unit.
	 */
	public void setJavaSource(String newSource) {
		try {
			mCompilationUnit.getBuffer().setContents(newSource);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Saves the compilation unit modifications.
	 */
	public void save() throws Exception {
		if (mCompilationUnitNode != null) {
			assert(mDocument != null); //see getCompilationUnitNode
			
			//computation of the text edits
			TextEdit edits = mCompilationUnitNode.rewrite(mDocument, mProject.getOptions(true));
			//computation of the new source code
			edits.apply(mDocument);
			String newSource = mDocument.get();
			// update of the compilation unit
			mCompilationUnit.getBuffer().setContents(newSource);
		}
		
		if (mCompilationUnit.isWorkingCopy()) {
			mCompilationUnit.commitWorkingCopy(true/*force*/, null/*monitor*/);
		} else {
			mCompilationUnit.save(null/*monitor*/, true/*force*/);
		}
	}
}
