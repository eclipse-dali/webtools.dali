package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NonIndexedMemberIndexedAnnotationAdapter;

public class JavaSecondaryTableResourceImpl extends AbstractTableResource implements JavaSecondaryTableResource
{	
	protected JavaSecondaryTableResourceImpl(JpaPlatform jpaPlatform, Member member, DeclarationAnnotationAdapter daa, IndexedAnnotationAdapter annotationAdapter) {
		super(jpaPlatform, member, daa, annotationAdapter);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> catalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__CATALOG);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__NAME);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> schemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__SCHEMA);
	}

	@Override
	public IndexedAnnotationAdapter getAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}
	
	public String getAnnotationName() {
		return JPA.SECONDARY_TABLE;
	}
		
	public Annotation annotation(CompilationUnit astRoot) {
		return getAnnotationAdapter().getAnnotation(astRoot);
	}
	
	public void moveAnnotation(int newIndex) {
		getAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	public void initializeFrom(JavaSingularTypeAnnotation oldAnnotation) {
		setName(((JavaSecondaryTableResource) oldAnnotation).getName());
		setCatalog(((JavaSecondaryTableResource) oldAnnotation).getCatalog());
		setSchema(((JavaSecondaryTableResource) oldAnnotation).getSchema());
	}
	
	// ********** static methods **********
	static JavaSecondaryTableResource createJavaSecondaryTable(JpaPlatform jpaPlatform, Member member) {
		return new JavaSecondaryTableResourceImpl(jpaPlatform, member, DECLARATION_ANNOTATION_ADAPTER, new NonIndexedMemberIndexedAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static JavaSecondaryTableResource createNestedJavaSecondaryTable(JpaPlatform jpaPlatform, Member member, int index, DeclarationAnnotationAdapter secondaryTablesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, secondaryTablesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new JavaSecondaryTableResourceImpl(jpaPlatform, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter secondaryTablesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(secondaryTablesAdapter, index, JPA.SECONDARY_TABLE);
	}
}
