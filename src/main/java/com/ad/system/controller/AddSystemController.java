package com.ad.system.controller;

import com.ad.system.model.Ad;
import com.ad.system.model.Campaign;
import com.ad.system.model.Client;
import com.ad.system.service.CampaignService;
import com.ad.system.service.ClientService;
import com.ad.system.service.SchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad/service/v1")
@RequiredArgsConstructor
@Tag(name = "Ad System API", description = "Operations related to Clients and Campaigns")
public class AddSystemController {
    private final ClientService clientService;
    private final CampaignService campaignService;
    private final SchedulerService schedulerService;
//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @Operation(
            summary = "Create a new client",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Client details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Client.class),
                            examples = @ExampleObject(
                                    name = "Client Example",
                                    value = """
                                        {
                                          "clientId": 1,
                                          "name": "Acme Corp",
                                          "contactInfo": "acme@example.com",
                                          "campaignIds": [1001, 1002]
                                        }
                                        """
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client created = clientService.createClient(client);
        return ResponseEntity.ok(created);
    }
//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @Operation(
            summary = "Get client by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }
//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @Operation(
            summary = "Get all clients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of clients returned")
            }
    )
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @Operation(
            summary = "Create a new campaign",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campaign details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Campaign.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Campaign created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping("/campaigns")
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        return ResponseEntity.ok(campaignService.createCampaign(campaign));
    }
//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @Operation(
            summary = "Get all campaigns",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of campaigns returned")
            }
    )
    @GetMapping("/campaigns")
    public ResponseEntity<Page<Campaign>> getAllCampaigns(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "createdAt") String sortBy,
                                                          @RequestParam(defaultValue = "desc") String sortDir) {
        Pageable pageable = sortDir.equalsIgnoreCase("desc") ?
                PageRequest.of(page, size, Sort.by(sortBy).descending()) :
                PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Campaign> campaignPage = campaignService.getAllCampaigns(pageable);
        return ResponseEntity.ok(campaignPage);
    }
//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @Operation(
            summary = "Get all active campaigns",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of active campaigns returned")
            }
    )
    @GetMapping("/campaigns/active")
    public ResponseEntity<List<Campaign>> getActiveCampaigns() {
        return ResponseEntity.ok(campaignService.getActiveCampaigns());
    }
//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @Operation(
            summary = "Activate a campaign by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Campaign activated"),
                    @ApiResponse(responseCode = "404", description = "Campaign not found")
            }
    )
    @PutMapping("/campaigns/{id}/activate")
    public ResponseEntity<Void> activateCampaign(@PathVariable Long id) {
        campaignService.activateCampaign(Campaign.builder().campaignId(id).build());
        return ResponseEntity.ok().build();
    }
//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @Operation(
            summary = "Deactivate a campaign by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Campaign deactivated"),
                    @ApiResponse(responseCode = "404", description = "Campaign not found")
            }
    )
    @PutMapping("/campaigns/{id}/deactivate")
    public ResponseEntity<Void> deactivateCampaign(@PathVariable Long id) {
        campaignService.deactivateCampaign(Campaign.builder().campaignId(id).build());
        return ResponseEntity.ok().build();
    }

//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @PostMapping("/ads/schedule")
    public ResponseEntity<Void> scheduleAd(@RequestBody Ad ad) {
        schedulerService.scheduleAd(ad);
        return ResponseEntity.ok().build();
    }

//========================<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=======================
    @PostMapping("/ads/{adId}/stop")
    public ResponseEntity<Void> stopAd(
            @PathVariable Long adId,
            @RequestParam String platformName) {
        schedulerService.stopAd(adId, platformName);
        return ResponseEntity.ok().build();
    }
}
