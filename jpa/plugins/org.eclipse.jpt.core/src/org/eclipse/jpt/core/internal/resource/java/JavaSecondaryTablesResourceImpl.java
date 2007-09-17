package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class JavaSecondaryTablesResourceImpl extends AbstractJavaAnnotationResource<Member> implements JavaSecondaryTablesResource
{
	private List<JavaSecondaryTableResource> secondaryTableResources;
	
	protected JavaSecondaryTablesResourceImpl(Member member, JpaPlatform jpaPlatform) {
		super(member, jpaPlatform, DECLARATION_ANNOTATION_ADAPTER);
		this.secondaryTableResources = new ArrayList<JavaSecondaryTableResource>();
	}

	public String getAnnotationName() {
		return JPA.SECONDARY_TABLES;
	}

	public String getSingularAnnotationName() {
		return JPA.SECONDARY_TABLE;
	}
		
	public ListIterator<JavaSecondaryTableResource> javaTypeAnnotations() {
		return new CloneListIterator<JavaSecondaryTableResource>(this.secondaryTableResources);
	}
	
	public int javaTypeAnnotationsSize() {
		return this.secondaryTableResources.size();
	}
	
	public JavaSecondaryTableResource add(int index) {
		JavaSecondaryTableResource javaSecondaryTableResource = createJavaSecondaryTable(index);
		this.secondaryTableResources.add(index, javaSecondaryTableResource);
		return javaSecondaryTableResource;
	}
	
	public void remove(JavaSecondaryTableResource javaTypeAnnotation) {
		this.secondaryTableResources.remove(javaTypeAnnotation);
	}
	
	public void remove(int index) {
		this.secondaryTableResources.remove(index);
	}
	
	public int indexOf(JavaSecondaryTableResource secondaryTableResource) {
		return this.secondaryTableResources.indexOf(secondaryTableResource);
	}
	
	public JavaSecondaryTableResource singularAnnotationAt(int index) {
		return this.secondaryTableResources.get(index);
	}
	
	public void move(int oldIndex, int newIndex) {
		this.secondaryTableResources.add(newIndex, this.secondaryTableResources.remove(oldIndex));
	}
	
	//TODO this is going to be copied in all JavaPluralTypeAnnotation implementations, how to solve that??
	public void updateFromJava(CompilationUnit astRoot) {
		List<JavaSecondaryTableResource> sTables = this.secondaryTableResources;
		int persSize = sTables.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaSecondaryTableResource secondaryTable = sTables.get(i);
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
				JavaSecondaryTableResource secondaryTable = this.createJavaSecondaryTable(javaSize);
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
	
	private JavaSecondaryTableResource createJavaSecondaryTable(int index) {
		return JavaSecondaryTableResourceImpl.createNestedJavaSecondaryTable(jpaPlatform(), getMember(), index, getDeclarationAnnotationAdapter());
	}

}
