package pl.grzegorz.eventapp.organizer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;
}