package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;

public abstract class AbstractTableResource extends AbstractJavaAnnotationResource<Member> implements JavaTableResource
{
	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	// hold this so we can get the 'schema' text range
	private final DeclarationAnnotationElementAdapter<String> schemaDeclarationAdapter;

	// hold this so we can get the 'catalog' text range
	private final DeclarationAnnotationElementAdapter<String> catalogDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> schemaAdapter;

	private final AnnotationElementAdapter<String> catalogAdapter;

	
	protected AbstractTableResource(JpaPlatform jpaPlatform, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(member, jpaPlatform, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.nameAdapter(daa);
		this.schemaDeclarationAdapter = this.schemaAdapter(daa);
		this.catalogDeclarationAdapter = this.catalogAdapter(daa);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(), this.nameDeclarationAdapter);
		this.schemaAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(), this.schemaDeclarationAdapter);
		this.catalogAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(), this.catalogDeclarationAdapter);
	}
	
	/**
	 * Build and return a declaration element adapter for the table's 'name' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	/**
	 * Build and return a declaration element adapter for the table's 'schema' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> schemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	/**
	 * Build and return a declaration element adapter for the table's 'catalog' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> catalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	private String name;
	
	private String catalog;
	
	private String schema;
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
		this.catalogAdapter.setValue(catalog);
	}

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
		this.schemaAdapter.setValue(schema);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.nameAdapter.getValue(astRoot));
		this.setSchema(this.schemaAdapter.getValue(astRoot));
		this.setCatalog(this.catalogAdapter.getValue(astRoot));
		//this.updateUniqueConstraintsFromJava(astRoot);
	}

}
