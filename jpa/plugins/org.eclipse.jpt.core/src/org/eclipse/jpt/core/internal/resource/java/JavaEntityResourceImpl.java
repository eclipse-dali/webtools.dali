package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;


public class JavaEntityResourceImpl extends AbstractJavaAnnotationResource<Type> implements JavaEntityResource
{
	private final AnnotationElementAdapter<String> nameAdapter;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();


	private String name;

	
	public JavaEntityResourceImpl(Type type, JpaPlatform jpaPlatform) {
		super(type, jpaPlatform, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(), NAME_ADAPTER);
	}
	
	public Iterator<JavaTypeAnnotationProvider> javaTypeAnnotationProviders() {
		return jpaPlatform().entityAnnotationProviders();
	}
	
	public String getAnnotationName() {
		return JPA.ENTITY;
	}
			
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void updateFromJava(CompilationUnit astRoot) {
		setName(this.nameAdapter.getValue(astRoot));
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ENTITY__NAME, false); // false = do not remove annotation when empty
	}

}
