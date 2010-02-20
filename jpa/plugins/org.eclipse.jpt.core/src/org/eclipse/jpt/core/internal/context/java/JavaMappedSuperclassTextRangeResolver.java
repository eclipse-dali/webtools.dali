package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;

public class JavaMappedSuperclassTextRangeResolver
	implements PrimaryKeyTextRangeResolver
{
	private JavaMappedSuperclass mappedSuperclass;
	
	private CompilationUnit astRoot;
	
	
	public JavaMappedSuperclassTextRangeResolver(
			JavaMappedSuperclass mappedSuperclass, CompilationUnit astRoot) {
		
		this.mappedSuperclass = mappedSuperclass;
		this.astRoot = astRoot;
	}
	
	
	public TextRange getTypeMappingTextRange() {
		return this.mappedSuperclass.getValidationTextRange(this.astRoot);
	}
	
	public TextRange getIdClassTextRange() {
		return this.mappedSuperclass.getIdClassReference().getValidationTextRange(this.astRoot);
	}
	
	public TextRange getAttributeMappingTextRange(String attributeName) {
		return this.mappedSuperclass.getPersistentType().
				getAttributeNamed(attributeName).getMapping().getValidationTextRange(this.astRoot);
	}
}
