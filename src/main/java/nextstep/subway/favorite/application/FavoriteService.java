package nextstep.subway.favorite.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.auth.application.dto.LoginMember;
import nextstep.subway.exception.IllegalFavoriteException;
import nextstep.subway.favorite.application.dto.FavoriteRequest;
import nextstep.subway.favorite.application.dto.FavoriteResponse;
import nextstep.subway.favorite.domain.Favorite;
import nextstep.subway.favorite.domain.FavoriteRepository;
import nextstep.subway.member.application.MemberService;
import nextstep.subway.member.domain.Member;
import nextstep.subway.path.application.PathService;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.station.application.StationService;
import nextstep.subway.station.domain.Station;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final StationService stationService;
    private final PathService pathService;
    private final MemberService memberService;

    @Transactional
    public Long createFavorite(LoginMember loginMember, FavoriteRequest request) {
        Member member = memberService.findMemberByEmailOrThrow(loginMember.getEmail());

        Station sourceStation = stationService.findStationByIdOrThrow(request.getSource());
        Station targetStation = stationService.findStationByIdOrThrow(request.getTarget());

        pathService.getPathOrThrow(new PathRequest(request.getSource(), request.getTarget()));

        Favorite favorite = new Favorite(member, sourceStation, targetStation);

        favoriteRepository.save(favorite);

        return favorite.getId();
    }

    public List<FavoriteResponse> findFavorites(LoginMember loginMember) {
        Member member = memberService.findMemberByEmailOrThrow(loginMember.getEmail());
        List<Favorite> favorites = favoriteRepository.findByMember(member);

        return favorites.stream()
                .map(f -> FavoriteResponse.of(f.getId(), f.getSourceStation(), f.getTargetStation()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFavorite(LoginMember loginMember, Long id) {
        Member member = memberService.findMemberByEmailOrThrow(loginMember.getEmail());

        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new IllegalFavoriteException("존재하지 않는 즐겨찾기입니다."));

        if (!favorite.isSameMember(member)) {
            throw new IllegalFavoriteException("즐겨찾기를 삭제할 수 없습니다.");
        }

        favoriteRepository.deleteById(id);
    }
}
