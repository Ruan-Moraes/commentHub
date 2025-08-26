package br.com.comment_hub.enums;

import java.util.Set;

/**
 * Constantes com os campos válidos para ordenação de cada entidade
 * <p>
 * Define os campos permitidos para evitar injeção de campos inválidos
 * nas consultas de ordenação.
 */
public final class SortFields {
    private SortFields() {
    }

    public static final Set<String> AUTHOR = Set.of(
            "id", "name", "email", "createdAt", "updatedAt"
    );
    public static final Set<String> POST = Set.of(
            "id", "title", "content", "createdAt", "updatedAt"
    );
    public static final Set<String> COMMENT = Set.of(
            "id", "content", "createdAt", "updatedAt"
    );
    public static final Set<String> USER = Set.of(
            "id", "name", "email", "createdAt", "updatedAt"
    );
    public static final String DEFAULT_AUTHOR_SORT = "id";
    public static final String DEFAULT_POST_SORT = "createdAt";
    public static final String DEFAULT_COMMENT_SORT = "createdAt";
    public static final String DEFAULT_USER_SORT = "id";
}
