package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.config;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.NotAuthorizedException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) throws NotAuthorizedException{
        Map<String, Object> realmAccess = source.getClaimAsMap("realm_access");
        if(Objects.nonNull(realmAccess)) {
            try{
                return ((List<String>) realmAccess.get("roles")).stream()
                        .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new NotAuthorizedException("Something went wrong while trying to extract the roles of the user.");
            }
        }
        return List.of();
    }
}


