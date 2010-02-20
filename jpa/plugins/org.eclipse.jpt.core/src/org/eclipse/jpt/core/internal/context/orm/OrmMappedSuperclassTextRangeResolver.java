package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;

public class OrmMappedSuperclassTextRangeResolver
	implements PrimaryKeyTextRangeResolver
{
	private OrmMappedSuperclass mappedSuperclass;
	
	
	public OrmMappedSuperclassTextRangeResolver(OrmMappedSuperclass mappedSuperclass) {
		this.mappedSuperclass = mappedSuperclass;
	}
	
	
	public TextRange getTypeMappingTextRange() {
		return this.mappedSuperclass.getValidationTextRange();
	}
	
	public TextRange getIdClassTextRange() {
		return this.mappedSuperclass.getIdClassReference().getValidationTextRange();
	}
	
	public TextRange getAttributeMappingTextRange(String attributeName) {
		return this.mappedSuperclass.getPersistentType().
				getAttributeNamed(attributeName).getMapping().getValidationTextRange();
	}
}
