package br.com.comment_hub.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

/**
 * Utilitário para criação segura de objetos Pageable e Sort
 * <p>
 * Esta classe fornece métodos estáticos para criar paginação com validação
 * de campos de ordenação, evitando NullPointerException e campos inválidos.
 */
public final class PaginationUtils {
    private PaginationUtils() {
    }

    /**
     * Cria um Pageable com validação de campos permitidos para ordenação
     *
     * @param page             número da página (0-based)
     * @param size             tamanho da página
     * @param sortBy           campo para ordenação (opcional)
     * @param allowedFields    campos permitidos para ordenação
     * @param defaultSortField campo padrão caso sortBy seja inválido
     * @return Pageable configurado
     */
    public static Pageable createPageableWithValidation(int page,
                                                        int size,
                                                        String sortBy,
                                                        Set<String> allowedFields,
                                                        String defaultSortField) {
        String sortField = resolveSortField(sortBy, allowedFields, defaultSortField);

        Sort sort = Sort.by(sortField).descending();

        return PageRequest.of(page, size, sort);
    }

    /**
     * Cria um Pageable sem validação de campos (mais permissivo)
     *
     * @param page             número da página (0-based)
     * @param size             tamanho da página
     * @param sortBy           campo para ordenação (opcional)
     * @param defaultSortField campo padrão caso sortBy seja inválido
     * @return Pageable configurado
     */
    public static Pageable createPageable(int page,
                                          int size,
                                          String sortBy,
                                          String defaultSortField) {
        String sortField = isValidSortField(sortBy) ? sortBy.trim() : defaultSortField;

        Sort sort = Sort.by(sortField).descending();

        return PageRequest.of(page, size, sort);
    }

    /**
     * Cria um Pageable sem ordenação quando sortBy for inválido
     *
     * @param page   número da página (0-based)
     * @param size   tamanho da página
     * @param sortBy campo para ordenação (opcional)
     * @return Pageable configurado (sem ordenação se sortBy for inválido)
     */
    public static Pageable createPageableOrUnsorted(int page,
                                                    int size,
                                                    String sortBy) {
        if (isValidSortField(sortBy)) {
            Sort sort = Sort.by(sortBy.trim()).descending();

            return PageRequest.of(page, size, sort);
        }

        return PageRequest.of(page, size);
    }

    /**
     * Resolve qual campo usar para ordenação baseado nas validações
     *
     * @param sortBy        campo solicitado
     * @param allowedFields campos permitidos
     * @param defaultField  campo padrão
     * @return campo final para usar na ordenação
     */
    private static String resolveSortField(String sortBy,
                                           Set<String> allowedFields,
                                           String defaultField) {
        if (isValidSortField(sortBy) && allowedFields.contains(sortBy.trim())) {
            return sortBy.trim();
        }

        return defaultField;
    }

    /**
     * Verifica se o campo de ordenação é válido (não nulo e não vazio)
     *
     * @param sortBy campo para validar
     * @return true se for válido
     */
    private static boolean isValidSortField(String sortBy) {
        return sortBy != null && !sortBy.trim().isEmpty();
    }
}
