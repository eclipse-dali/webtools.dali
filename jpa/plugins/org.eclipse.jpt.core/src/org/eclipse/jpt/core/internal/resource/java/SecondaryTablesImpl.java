/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class SecondaryTablesImpl extends AbstractJavaAnnotationResource<Member> implements SecondaryTables
{
	private List<SecondaryTable> secondaryTableResources;
	
	protected SecondaryTablesImpl(Member member, JpaPlatform jpaPlatform) {
		super(member, jpaPlatform, DECLARATION_ANNOTATION_ADAPTER);
		this.secondaryTableResources = new ArrayList<SecondaryTable>();
	}

	public String getAnnotationName() {
		return JPA.SECONDARY_TABLES;
	}

	public String getSingularAnnotationName() {
		return JPA.SECONDARY_TABLE;
	}
		
	public ListIterator<SecondaryTable> javaTypeAnnotations() {
		return new CloneListIterator<SecondaryTable>(this.secondaryTableResources);
	}
	
	public int javaTypeAnnotationsSize() {
		return this.secondaryTableResources.size();
	}
	
	public SecondaryTable add(int index) {
		SecondaryTable javaSecondaryTableResource = createJavaSecondaryTable(index);
		this.secondaryTableResources.add(index, javaSecondaryTableResource);
		return javaSecondaryTableResource;
	}
	
	public void remove(SecondaryTable javaTypeAnnotation) {
		this.secondaryTableResources.remove(javaTypeAnnotation);
	}
	
	public void remove(int index) {
		this.secondaryTableResources.remove(index);
	}
	
	public int indexOf(SecondaryTable secondaryTableResource) {
		return this.secondaryTableResources.indexOf(secondaryTableResource);
	}
	
	public SecondaryTable singularAnnotationAt(int index) {
		return this.secondaryTableResources.get(index);
	}
	
	public void move(int oldIndex, int newIndex) {
		this.secondaryTableResources.add(newIndex, this.secondaryTableResources.remove(oldIndex));
	}
	
	//TODO this is going to be copied in all JavaPluralTypeAnnotation implementations, how to solve that??
	public void updateFromJava(CompilationUnit astRoot) {
		List<SecondaryTable> sTables = this.secondaryTableResources;
		int persSize = sTables.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			SecondaryTable secondaryTable = sTables.get(i);
			if (secondaryTable.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			secondaryTable.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model secondary tables beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				sTables.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				SecondaryTable secondaryTable = this.createJavaSecondaryTable(javaSize);
				if (secondaryTable.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					this.secondaryTableResources.add(secondaryTable);
					secondaryTable.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}
	
	private SecondaryTable createJavaSecondaryTable(int index) {
		return SecondaryTableImpl.createNestedJavaSecondaryTable(jpaPlatform(), getMember(), index, getDeclarationAnnotationAdapter());
	}

}
