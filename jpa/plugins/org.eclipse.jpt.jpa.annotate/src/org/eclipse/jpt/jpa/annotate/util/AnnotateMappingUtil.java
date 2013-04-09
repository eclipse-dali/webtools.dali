/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.StringTools;

public class AnnotateMappingUtil
{
	public static final String[] REPEATING_TYPES = new String[]
    {
		java.util.Collection.class.getName(),
		java.util.Enumeration.class.getName(),
		java.util.Iterator.class.getName(),
		java.util.Map.class.getName()
    };	
	
    /** java primitive types */
    public static String[] PRIMITIVE_TYPES = new String[] { "int",      //$NON-NLS-1$
                                                            "double",   //$NON-NLS-1$
                                                            "long",     //$NON-NLS-1$
                                                            "short",    //$NON-NLS-1$
                                                            "byte",     //$NON-NLS-1$
                                                            "float",    //$NON-NLS-1$
                                                            "boolean",  //$NON-NLS-1$
                                                            "char",     //$NON-NLS-1$
                                                            "void" };   //$NON-NLS-1$
    public static String[] PRIMITIVE_WRAPPERS = new String[] {Integer.class.getName(),
        Double.class.getName(),
        Long.class.getName(),
        Short.class.getName(),
        Byte.class.getName(),
        Float.class.getName(),
        Boolean.class.getName(),
        Character.class.getName()};
	
    public final static String GET_PREFIX = "get";  //$NON-NLS-1$
    public final static String IS_PREFIX  = "is";   //$NON-NLS-1$
    public final static String BOOLEAN = "boolean"; //$NON-NLS-1$
    
    public static boolean isRepeatingType(String type, IProject project) throws JavaModelException
	{
		if (isArrayType(type))
			return true;
		String strippedType = stripTypeParameters(type);
		for (String repeatingType : REPEATING_TYPES)
		{
			if (strippedType.equals(repeatingType) ||
					isAssignableFrom(strippedType, repeatingType, project))
				return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the properties in refType of the same type entityClass.
	 * 
	 */
	public static Set<String> getMappedByList(String entityClass, IType refType, boolean isManyToMany)
		throws JavaModelException
	{
		Set<String> propNames = new java.util.HashSet<String>();
		IField[] fields = refType.getFields();
		for (IField field : fields)
		{
			// filter out static/inherited fields
			if (Flags.isStatic(field.getFlags()) || Flags.isSuper(field.getFlags()))
				continue;
			
			String fieldType = getFieldType(field, refType);
			String dt = null;
			if (fieldType != null)
			{
				if (isManyToMany) 
				{
					dt = getGenericsComponentTypeName(fieldType);
					if (dt != null)
						dt = resolveType(dt, refType);
				} else 
				{
					dt = fieldType;
				}
			}
			if (dt != null && dt.equals(entityClass)) 
			{
				propNames.add(field.getElementName());
			}
		}
		return propNames;
	}

	public static String getGenericsComponentTypeName(String s) 
	{
		//s is in the form T<x, y>
		int i1 = s.indexOf('<');
		if (i1 < 0) {
			return null;
		}
		int i2 = s.indexOf('>', i1);
		if (i2 < 0) {
			return null;
		}
		String typeName = s.substring(i1+1, i2);
		//don't bother with the Map case for now
		if (typeName.indexOf(',') >= 0) {
			return null;
		}
		
		return typeName;
	}
	
	/**
	 * returns the fully qualified type for an IField
	 * @param field
	 * @param containingType
	 * @return
	 * @throws JavaModelException
	 */
	public static String getFieldType(IField field, IType containingType) throws JavaModelException
	{
		String shortType = field.getTypeSignature();
		shortType = Signature.toString(shortType);
		if (ClassNameTools.isPrimitive(shortType))
			return shortType;
		String type = resolveType(shortType, containingType);
		return type;
	}
	
	public static boolean isNumeric(String type)
	{
		return (type.equals(Integer.class.getName()) || type.equals(Integer.TYPE.getName())
				|| type.equals(Float.class.getName()) || type.equals(Float.TYPE.getName())
				|| type.equals(Double.class.getName()) || type.equals(Double.TYPE.getName())
				|| type.equals(Short.class.getName()) || type.equals(Short.TYPE.getName())
				|| type.equals(Long.class.getName()) || type.equals(Long.TYPE.getName())
				|| type.equals(Byte.class.getName()) || type.equals(Byte.TYPE.getName()) 
				|| type.equals(Number.class.getName())
				|| type.equals(BigDecimal.class.getName()));	
	}
	
	public static boolean isString(String type)
	{
		return (type.equals(String.class.getName()));
	}
		
	public static boolean isDate(String type, IProject proj) throws JavaModelException
	{
		return isAssignableFrom(type, Date.class.getName(), proj) ||
			isAssignableFrom(type, Calendar.class.getName(), proj);
	}
	
	public static boolean isBoolean(String type)
	{
		return (type.equals(Boolean.class.getName()) || type.equals(Boolean.TYPE.getName()));
	}
	
    /**
     * Checks if this type is an array type ie type has [] at the end.
     */
    public static boolean isArrayType( String type )
    {
        return type.endsWith( "[]" ); //$NON-NLS-1$
    }

    public static boolean hasMatchingTypeParameterBraces( String type )
    {
        assert type != null : "Type was null."; //$NON-NLS-1$
        char[] chars = type.toCharArray();
        int openCount = 0;
        int closeCount = 0;
        
        for ( int i = 0; i < chars.length; ++i )
        {
            if ( chars[i] == '<' )
                ++openCount;
            
            if ( chars[i] == '>' )
                ++closeCount;
            
            if ( closeCount > openCount )
                return false;
        }
        
        return openCount == closeCount; 
    }
    
    /**
     * returns true if the array brackets on the string type are properly matched
     * false otherwise
     */
    public static boolean hasMatchingArrayBrackets( String type )
    {
        int firstBracket = type.indexOf( "[" ); //$NON-NLS-1$
        int nextBracket  = type.indexOf( "]" ); //$NON-NLS-1$
        
        // if no brackets at all, return true
        if ( firstBracket == -1 && nextBracket == -1 )
        {
            return true;
        }
        
        String tempType = type;
        
        while( tempType.length() > 0 )
        {
            nextBracket = tempType.indexOf( "]" ); //$NON-NLS-1$
            if ( nextBracket < firstBracket || 
                    firstBracket == -1 ||
                    nextBracket != firstBracket + 1 )
            {
                return false;
            }
            tempType = tempType.substring( nextBracket + 1 );
            firstBracket = tempType.indexOf( "[" ); //$NON-NLS-1$
        }
        return true;
    }
    
    
    /**
     * Strips type parameter information from a type name, for example "java.util.List<java.lang.String>"
     * would become "java.util.List". Stripping "<>" would become "".  If the "<" don't match, then
     * the provided type name is returned as-is, for example "List<<>" would be returned as "List<<>".
     * This will not return null.
     */
    public static String stripTypeParameters( String typeName )
    {
        assert typeName != null : "Type name was null."; //$NON-NLS-1$
        
        if ( hasMatchingTypeParameterBraces( typeName ) )
        {
	        int index = typeName.indexOf( '<' );
	        
	        if ( index > -1 )
	            typeName = typeName.substring( 0, index ).trim();
        }
	        
        assert typeName != null : "Returning null type.";  //$NON-NLS-1$
        return typeName;
    }
    
    /**
     * Strips any array dimensions off of the provided type name.
     * For example, stripping "Foo[]" will return "Foo".  If the brackets
     * are not matched, the type name will be returned unchanged.  For example,
     * stripping "Foo]" will return "Foo]", and stripping "Foo[][" will 
     * return "Foo[][".  Stripping "[]" will return "".  Null is not returned.
     */
    public static String stripArrayDimensions( String typeName )
    {
        assert typeName != null : "Type name was null.";  //$NON-NLS-1$
        
        if ( hasMatchingArrayBrackets( typeName ) )
        {
	        typeName = stripTypeParameters( typeName ); // Deal with things like Collection<String[]>
	        int index = typeName.indexOf( '[' );

	        if( index > -1 )
	            typeName = typeName.substring( 0, index ).trim();
        }
        
        assert typeName != null : "Returning null type.";  //$NON-NLS-1$
        return typeName;
    }
    
    
    /**
     * Retrieves an IType for a given Java project or any project it references.
     * Anonymous types cannot be retrieved with this method.  
     * @param fullyQualifiedType The fully qualified name of the type - for example "java.lang.String".  
     *        This doesn't work for primitive or array types, such as "int" or "Foo[]".
     * @param project The project that is in scope.
     */
    public static IType getType( String fullyQualifiedType, IProject project ) throws JavaModelException
    {
        assert fullyQualifiedType != null : "Fully qualified type should not be null."; //$NON-NLS-1$
        
        // the JDT returns a non-null anonymous class IType 
        // for empty string and package names that end with a dot
        // if the type starts with a dot, the JDT helpfully removes it 
        // and returns the type referenced without the dot
        // short circuit here for perf and so validation results make sense
        // e.g. if the valid type is "Thing", then ".Thing" and "Thing." should not be valid
        if ( fullyQualifiedType.trim().length() == 0 
                || fullyQualifiedType.startsWith(".") //$NON-NLS-1$
                        || fullyQualifiedType.endsWith(".")) //$NON-NLS-1$
            return null;
        
        int ltIndex = fullyQualifiedType.indexOf( '<' );
        int gtIndex = fullyQualifiedType.lastIndexOf( '>' );
        if ( ltIndex != -1 && gtIndex != -1 && gtIndex > ltIndex )
            assert false : 
                "Type should have type parameter info stripped before calling this method: " + fullyQualifiedType; //$NON-NLS-1$
        assert !isArrayType( fullyQualifiedType ) : "Type should have array brackets stripped before calling: " + //$NON-NLS-1$
            fullyQualifiedType;
        assert project != null : "Project should not be null."; //$NON-NLS-1$

        // if the project is not accessible, this will cause a JavaModelException below
        if ( !project.isAccessible() )
        {
            return null;
        }
        
        IJavaProject javaProject = JavaCore.create( project );
        
        if (( javaProject == null ) || ( !javaProject.exists() ))
        {
            return null;
        }

        IType type = null;
        type = javaProject.findType( fullyQualifiedType );
        if ( type != null && ( !type.exists() || type.isAnonymous() ) )
        {
        	type = null;
        }
        return type;
    }

    /**
     * Determines if the class or interface represented by the subtype name is either the same as, or is a superclass
     * or superinterface of, the class or interface represented by the specified supertype name. It returns true if
     * so; otherwise it returns false.  This method will not block and wait if the required compiler information is not
     * instantly available - in this case it will return false right away.  Callers should be aware that false can
     * sometimes mean that "we could neither confirm nor deny that the type is assignable from the super type".  Callers
     * should handle this by calling this method as often as needed and not caching the result.  Type parameter
     * information should be removed before calling this method (see {@link #stripTypeParameters(String)} for
     * this purpose).
     * @param fullyQualifiedSubTypeName The subtype name.
     * @param fullyQualifiedSuperTypeName The supertype name.
     * @param project The relevant project.  If null is passed for this parameter, then it attempts to
     *                determine the active Java project based on the current editor.  If no Java project can be
     *                determined, then this method will definitley return false.
     */
    public static boolean isAssignableFrom( String fullyQualifiedSubTypeName, String fullyQualifiedSuperTypeName,
                                            IProject project ) throws JavaModelException
    {
        assert fullyQualifiedSubTypeName != null : "Sub type name was null."; //$NON-NLS-1$
        assert fullyQualifiedSuperTypeName != null : "Super type name was null."; //$NON-NLS-1$
        assert fullyQualifiedSubTypeName.indexOf( '<' ) == -1 : 
            "Subtype should have type parameter info stripped before calling this method: " +  //$NON-NLS-1$
            fullyQualifiedSubTypeName;
        assert fullyQualifiedSuperTypeName.indexOf( '<' ) == -1 : 
            "Supertype should have type parameter info stripped before calling this method: " +  //$NON-NLS-1$
            fullyQualifiedSuperTypeName;
        boolean assignableFrom = false;
        
        if ( project != null )
        {
            IType subType = getType( fullyQualifiedSubTypeName, project );
            IType superType = getType( fullyQualifiedSuperTypeName, project );
            
            if ( subType != null && superType != null )
            {
                ITypeHierarchy th = subType.newSupertypeHierarchy( null );
                assert th != null : "Type hierarchy was null."; //$NON-NLS-1$
                assignableFrom = th.contains( superType );
            }
        }
        
        return assignableFrom;
    }
 
     /**
      * Resolves a type name against an IType to get a fully qualified type name. If
      * it cannot be resolved for some reason (eg. the type is ambiguous), this returns null.
      */
     public static String resolveType( String typeName, IType type ) throws JavaModelException
     {
         assert typeName != null : "Type name was null."; //$NON-NLS-1$
         assert type != null : "Type was null."; //$NON-NLS-1$
         int bracketIndex = typeName.indexOf( '[' );
         int typeParamIndex = typeName.indexOf( '<' );
         int suffixIndex = -1;
         String typeSuffix = null;
         
         if ( bracketIndex > -1 && typeParamIndex > -1 )
             suffixIndex = bracketIndex <= typeParamIndex ? bracketIndex : typeParamIndex;
         else if ( bracketIndex > -1 )
             suffixIndex = bracketIndex;
         else if ( typeParamIndex > -1 )
             suffixIndex = typeParamIndex;
         
         if ( suffixIndex > -1 )
         {
             typeSuffix = typeName.substring( suffixIndex );
             typeName = typeName.substring( 0, suffixIndex ).trim();
         }
         
         String value = null;
         String[][] typeMatches = type.resolveType( typeName );
         
         // If typeMatches.length > 1 then the type was ambiguous.  We return null in that case.
         if ( typeMatches != null && typeMatches.length == 1 )
         {
             value = ( typeMatches[0][0].length() == 0 ?  typeMatches[0][1] :
                 typeMatches[0][0] + "." + typeMatches[0][1] ); //$NON-NLS-1$
         }
         
         if ( value == null && type.isBinary() )
         {
             // For some reason, resolving types against binary types doesn't always 
             // (ever?) work.  If the type we're trying to resolve it against is binary,
             // try to resolve against the project instead (and since there should only
             // be one reachable type with a given fully qualified name on the project 
             // classpath anyway, this should be legit).
             IProject project = type.getJavaProject().getProject();
             assert project != null : "Project was null.";  //$NON-NLS-1$
             IType resolvedType = getType( typeName, project );
             
             if ( resolvedType != null )
                 value = resolvedType.getFullyQualifiedName( '.' );
         }
         
         if ( value != null && typeSuffix != null )
             value += typeSuffix;
             
         return value;
     }
 
 
     /**
      * Strips out package information and returns the java class name
      * If the class uses &lt;&gt;, then keep that in.
      * Examples: <pre>
      *  getClassName("MyClass") = "MyClass"
      *  getClassName("java.util.ArrayList") = "ArrayList"
      *  getClassName("java.util.String[]") = "String[]"
      *  getClassName("java.util.ArrayList&lt;java.lang.String&gt;") = "ArrayList&lt;String&gt;"
      *  getClassName("java.util.ArrayList&lt;java.lang.ArrayList&lt;String&gt;&gt;") = ArrayList&lt;ArrayList&lt;String&gt;&gt;
      *  getClassName("java.util.ArrayList&lt;java.lang.String[]&gt;[]") = ArrayList&lt;String[]&gt;[]
      * </pre>
      * @param fullClassName the fully qualified class name
      * @return the name of the class
      */
     public static String getClassName( String fullClassName )
     {
         if ( fullClassName == null )
         {
             return null;
         }
         else
         {
             int endBracket = fullClassName.lastIndexOf('>');  //$NON-NLS-1$
             if (endBracket >= 0)
             {
                 int beginBracket = fullClassName.indexOf('<');  //$NON-NLS-1$
                 assert beginBracket >= 0 : "no matching '<' for '>' in " + fullClassName;  //$NON-NLS-1$
                 assert beginBracket < endBracket : "bad class syntax in " + fullClassName; //$NON-NLS-1$
                 return (getClassName(fullClassName.substring(0, beginBracket)) + "<" + //$NON-NLS-1$
                         getClassName(fullClassName.substring(beginBracket + 1, endBracket)) + ">" +  //$NON-NLS-1$
                         fullClassName.substring(endBracket + 1));
             }
             else
             {
                 int dotpos = fullClassName.lastIndexOf( '.' );
                 return ( dotpos < 0 )? fullClassName : fullClassName.substring( dotpos + 1 );
             }
         }
     }

     /******************************************************************************
      * Determines is two objects are equal, accounting for one or both
      * objects being null or the two objects being array types.
      * @param o1  The first object to compare.
      * @param o2  The second object to compare.
      * @return  True if the two objects are equal.
      ******************************************************************************/
     public static final boolean areEqual( Object o1, Object o2 )
     {
         boolean objectsAreEqual = false;
         if( o1 == o2 )
         {
             objectsAreEqual = true;
         }
         else if ( o1 != null && o2 != null )
         {
             if ( o1.getClass().isArray() && o2.getClass().isArray() )
             {
                 objectsAreEqual = Arrays.equals(
                     ( Object[] ) o1, ( Object[] ) o2 );
             }
             else
             {
                 objectsAreEqual = o1.equals( o2 );
             }
         }

         return objectsAreEqual;
     }
     
     public static String dbNameToJavaName(String dbName)
     {
		String result = dbName;
		if (StringTools.isUppercase(result) || StringTools.isLowercase(result)) {
			// leave mixed case identifiers alone?
			result = StringTools.convertAllCapsToCamelCase(result, false);
		}
		result = NameTools.convertToJavaIdentifier(result);
		return result;    	 
     }
     
 	/**
 	 * Utility methods used to convert DB object names to  
 	 * appropriate Java type and field name 
 	 */
 	public static String pluralise(String name) {
 		String result = name;
 		if (name.length() == 1) {
 			result += 's';
 		} else if (!seemsPluralised(name)) {
 			String lower = name.toLowerCase();
 			if (!lower.endsWith("data")) { //orderData --> orderDatas is dumb
 				char secondLast = lower.charAt(name.length() - 2);
 				if (!isVowel(secondLast) && lower.endsWith("y")) {
 					// city, body etc --> cities, bodies
 					result = name.substring(0, name.length() - 1) + "ies";
 				} else if (lower.endsWith("ch") || lower.endsWith("s")) {
 					// switch --> switches  or bus --> buses
 					result = name + "es";
 				} else {
 					result = name + "s";
 				}
 			}
 		}
 		return result;
 	}

 	public static String singularise(String name) {
 		String result = name;
 		if (seemsPluralised(name)) {
 			String lower = name.toLowerCase();
 			if (lower.endsWith("ies")) {
 				// cities --> city
 				result = name.substring(0, name.length() - 3) + "y";
 			} else if (lower.endsWith("ches") || lower.endsWith("ses")) {
 				// switches --> switch or buses --> bus
 				result = name.substring(0, name.length() - 2);
 			} else if (lower.endsWith("s")) {
 				// customers --> customer
 				result = name.substring(0, name.length() - 1);
 			}
 		}
 		return result;
 	}
 	private final static boolean isVowel(char c) {
 		boolean vowel = false;
 		vowel |= c == 'a';
 		vowel |= c == 'e';
 		vowel |= c == 'i';
 		vowel |= c == 'o';
 		vowel |= c == 'u';
 		vowel |= c == 'y';
 		return vowel;
 	}
 	private static boolean seemsPluralised(String name) {
 		name = name.toLowerCase();
 		boolean pluralised = false;
 		pluralised |= name.endsWith("es");
 		pluralised |= name.endsWith("s");
 		pluralised &= !(name.endsWith("ss") || name.endsWith("us"));
 		return pluralised;
 	}
 	
}
