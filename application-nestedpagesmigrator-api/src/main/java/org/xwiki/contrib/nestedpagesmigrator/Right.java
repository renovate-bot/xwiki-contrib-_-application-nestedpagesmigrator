/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.nestedpagesmigrator;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.xwiki.model.reference.DocumentReference;

/**
 * @version $Id: $
 */
public class Right implements Serializable
{
    private DocumentReference user;

    private DocumentReference group;

    private String level;

    private boolean allow;

    private DocumentReference origin;

    private boolean enabled = true;

    public Right(DocumentReference user, DocumentReference group, String level, boolean allow, DocumentReference origin)
    {
        this.user = user;
        this.group = group;
        this.level = level;
        this.allow = allow;
        this.origin = origin;
    }

    public Right(DocumentReference user, DocumentReference group, String level, boolean allow,
            DocumentReference origin, boolean enabled)
    {
        this.user = user;
        this.group = group;
        this.level = level;
        this.allow = allow;
        this.origin = origin;
        this.enabled = enabled;
    }

    public DocumentReference getUser()
    {
        return user;
    }

    public DocumentReference getGroup()
    {
        return group;
    }

    public String getLevel()
    {
        return level;
    }

    public boolean isAllow()
    {
        return allow;
    }

    public DocumentReference getOrigin()
    {
        return origin;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public String toString()
    {
        DocumentReference target = user;
        String type = "user";
        if (user == null) {
            target = group;
            type = "group";
        }

        return String.format("Right [%s %s, %s, %s, %s]", type, target, level, allow ? "allow" : "deny", origin);
    }

    public boolean hasSameConcern(Right otherRight)
    {
        return new EqualsBuilder().append(user, otherRight.user).append(group, otherRight.group)
                .append(level, otherRight.level).isEquals();
    }

    public boolean hasSameEffect(Right otherRight)
    {
        return allow == otherRight.allow && hasSameConcern(otherRight);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Right) {
            Right otherRight = (Right) o;
            return hasSameConcern(otherRight) && new EqualsBuilder().append(allow, otherRight.allow)
                    .append(origin, otherRight.origin).isEquals();
        }

        return false;
    }

    public Right getInverseRight()
    {
        return new Right(user, group, level, !allow, origin);
    }
}
