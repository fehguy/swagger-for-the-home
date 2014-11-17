#import <Foundation/Foundation.h>
#import "SWGObject.h"
#import "SWGTemperature.h"


@interface SWGTemperatureForecast : SWGObject

@property(nonatomic) NSNumber* dt;  

@property(nonatomic) SWGTemperature* temp;  

@property(nonatomic) NSNumber* pressure;  

@property(nonatomic) NSNumber* humidity;  

@property(nonatomic) NSArray* weather;  

@property(nonatomic) NSNumber* speed;  

@property(nonatomic) NSNumber* deg;  

@property(nonatomic) NSNumber* clouds;  

- (id) dt: (NSNumber*) dt
     temp: (SWGTemperature*) temp
     pressure: (NSNumber*) pressure
     humidity: (NSNumber*) humidity
     weather: (NSArray*) weather
     speed: (NSNumber*) speed
     deg: (NSNumber*) deg
     clouds: (NSNumber*) clouds;

- (id) initWithValues: (NSDictionary*)dict;
- (NSDictionary*) asDictionary;


@end

