//
//  FirstViewController.h
//  SFTHClient
//
//  Created by Tony Tam on 11/1/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import <CoreBluetooth/CoreBluetooth.h>
#import "SWGZwaveApi.h"

@interface FirstViewController : UIViewController<CLLocationManagerDelegate> {
    bool connected;

    UIButton* logoButton;
    
    UISwitch* switch1;
    UISwitch* switch2;
    UISwitch* switch3;
    UISwitch* switch4;
    UISwitch* switch5;
    UISwitch* switch6;
    UISwitch* switch7;
    UISwitch* switch8;
    CLLocationManager *locationManager;
    CLBeaconRegion *beaconRegion;
}

-(IBAction)switch1ValueChanged:(id)sender;
-(IBAction)switch1Timer:(id)sender;

-(IBAction)switch2ValueChanged:(id)sender;
-(IBAction)switch2Timer:(id)sender;

-(IBAction)switch3ValueChanged:(id)sender;
-(IBAction)switch3Timer:(id)sender;

-(IBAction)switch4ValueChanged:(id)sender;
-(IBAction)switch4Timer:(id)sender;

-(IBAction)switch5ValueChanged:(id)sender;
-(IBAction)switch5Timer:(id)sender;

-(IBAction)switch6ValueChanged:(id)sender;
-(IBAction)switch6Timer:(id)sender;

-(IBAction)switch7ValueChanged:(id)sender;
-(IBAction)switch7Timer:(id)sender;

-(IBAction)switch8ValueChanged:(id)sender;
-(IBAction)switch8Timer:(id)sender;

-(IBAction)switch9ValueChanged:(id)sender;
-(IBAction)switch9Timer:(id)sender;

@property (nonatomic, retain) IBOutlet UIButton* logoButton;

@property (nonatomic, retain) IBOutlet UISwitch* switch1;
@property (nonatomic, retain) IBOutlet UISwitch* switch2;
@property (nonatomic, retain) IBOutlet UISwitch* switch3;
@property (nonatomic, retain) IBOutlet UISwitch* switch4;
@property (nonatomic, retain) IBOutlet UISwitch* switch5;
@property (nonatomic, retain) IBOutlet UISwitch* switch6;
@property (nonatomic, retain) IBOutlet UISwitch* switch7;
@property (nonatomic, retain) IBOutlet UISwitch* switch8;
@property (nonatomic, retain) IBOutlet UISwitch* switch9;

@property (nonatomic, retain) CLLocationManager *locationManager;
@property (nonatomic, retain) CLBeaconRegion *beaconRegion;

@property (strong, nonatomic) NSDictionary *beaconPeripheralData;
@property (strong, nonatomic) CBPeripheralManager *peripheralManager;


-(IBAction)refreshState;

@end

