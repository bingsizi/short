package com.project.base.security.shiro.permission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.DomainPermission;
import org.apache.shiro.util.CollectionUtils;

/**  
 * url授权判定
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月10日 下午4:43:00
 */
public class URLPermission implements Permission,Serializable{

    //TODO - JavaDoc methods

    /**
	 * serialVersionUID long
	 * created by zhangpeiran 2016年5月11日 上午8:54:32
	 */
	private static final long serialVersionUID = -6917858722000747179L;
	/*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/
    protected static final String WILDCARD_TOKEN = "*";
    protected static final String PART_DIVIDER_TOKEN = "/";
    protected static final String SUBPART_DIVIDER_TOKEN = ",";
    protected static final boolean DEFAULT_CASE_SENSITIVE = false;

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    private List<Set<String>> parts;

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/
    /**
     * Default no-arg constructor for subclasses only - end-user developers instantiating Permission instances must
     * provide a wildcard string at a minimum, since Permission instances are immutable once instantiated.
     * <p/>
     * Note that the URLPermission class is very robust and typically subclasses are not necessary unless you
     * wish to create type-safe Permission objects that would be used in your application, such as perhaps a
     * {@code UserPermission}, {@code SystemPermission}, {@code PrinterPermission}, etc.  If you want such type-safe
     * permission usage, consider subclassing the {@link DomainPermission DomainPermission} class for your needs.
     */
    protected URLPermission() {
    }

    public URLPermission(String wildcardString) {
        this(wildcardString, DEFAULT_CASE_SENSITIVE);
    }

    public URLPermission(String wildcardString, boolean caseSensitive) {
        setParts(wildcardString, caseSensitive);
    }

    protected void setParts(String wildcardString) {
        setParts(wildcardString, DEFAULT_CASE_SENSITIVE);
    }

    protected void setParts(String wildcardString, boolean caseSensitive) {
        if (wildcardString == null || wildcardString.trim().length() == 0) {
            throw new IllegalArgumentException("Wildcard string cannot be null or empty. Make sure permission strings are properly formatted.");
        }

        wildcardString = wildcardString.trim();

        List<String> parts = CollectionUtils.asList(wildcardString.split(PART_DIVIDER_TOKEN));

        this.parts = new ArrayList<Set<String>>();
        for (String part : parts) {
            Set<String> subparts = CollectionUtils.asSet(part.split(SUBPART_DIVIDER_TOKEN));
            if (!caseSensitive) {
                subparts = lowercase(subparts);
            }
            if (subparts.isEmpty()) {
                throw new IllegalArgumentException("Wildcard string cannot contain parts with only dividers. Make sure permission strings are properly formatted.");
            }
            this.parts.add(subparts);
        }

        if (this.parts.isEmpty()) {
            throw new IllegalArgumentException("Wildcard string cannot contain only dividers. Make sure permission strings are properly formatted.");
        }
    }

    private Set<String> lowercase(Set<String> subparts) {
        Set<String> lowerCasedSubparts = new LinkedHashSet<String>(subparts.size());
        for (String subpart : subparts) {
            lowerCasedSubparts.add(subpart.toLowerCase());
        }
        return lowerCasedSubparts;
    }

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/
    protected List<Set<String>> getParts() {
        return this.parts;
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    public boolean implies(Permission p) {
        // By default only supports comparisons with other URLPermissions
        if (!(p instanceof URLPermission)) {
            return false;
        }

        URLPermission wp = (URLPermission) p;

        List<Set<String>> otherParts = wp.getParts();

        int i = 0;
        for (Set<String> otherPart : otherParts) {
            // If this permission has less parts than the other permission, everything after the number of parts contained
            // in this permission is automatically implied, so return true
            if (getParts().size() - 1 < i) {
                return true;
            } else {
                Set<String> part = getParts().get(i);
                if (!part.contains(WILDCARD_TOKEN) && !part.containsAll(otherPart)) {
                    return false;
                }
                i++;
            }
        }

        // If this permission has more parts than the other parts, only imply it if all of the other parts are wildcards
        for (; i < getParts().size(); i++) {
            Set<String> part = getParts().get(i);
            if (!part.contains(WILDCARD_TOKEN)) {
                return false;
            }
        }

        return true;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (Set<String> part : parts) {
            if (buffer.length() > 0) {
                buffer.append(":");
            }
            buffer.append(part);
        }
        return buffer.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof URLPermission) {
            URLPermission wp = (URLPermission) o;
            return parts.equals(wp.parts);
        }
        return false;
    }

    public int hashCode() {
        return parts.hashCode();
    }
}
